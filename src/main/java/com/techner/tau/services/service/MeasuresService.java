package com.techner.tau.services.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.common.entity.ActualMeasuresResult;
import com.techner.tau.common.entity.Measure;
import com.techner.tau.common.entity.MeasureResult;
import com.techner.tau.common.entity.Variable;
import com.techner.tau.services.mapper.MeasuresMapper;

public class MeasuresService {
    /** CTE lluvia */
    private final static String PRECIPITATION_CODE = "L";
    /** Evapotraspiration code */
    private final static String EVAP_CODE = "E";
    /** Mapper */
    private final MeasuresMapper mapper;
    /** Factory */
    private final ActualMeasuresResult.Factory factory;
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(MeasuresService.class);

    /**
     * @param mapper
     */
    @Inject
    public MeasuresService(MeasuresMapper mapper, ActualMeasuresResult.Factory factory) {
        this.mapper = mapper;
        this.factory = factory;
    }

    /**
     * Obtiene medidas actuales
     * 
     * @param idStation
     *            id de la estacion
     * @return lista de medidas
     */
    public ActualMeasuresResult getActualMeasures(Integer idStation, boolean gralInfo) {
        List<String> codes = mapper.getEnableVariables(idStation);
        return getActualMeasures(idStation, codes, gralInfo);
    }

    /**
     * Obtiene medidas actuales
     * 
     * @param idStation
     *            id de la estacion
     * @return lista de medidas
     */
    public ActualMeasuresResult getActualMeasures(Integer idStation, List<String> codes, boolean gralInfo) {
        ActualMeasuresResult result = null;
        List<MeasureResult> listMeasures = new ArrayList<MeasureResult>();

        List<String> excludedIds = new ArrayList<String>();
        if (codes.contains(PRECIPITATION_CODE)) {
            excludedIds.add(PRECIPITATION_CODE);
            MeasureResult measure = mapper.getAcumVariable(idStation, Measure.Type.actual.toString(),
                    PRECIPITATION_CODE);
            if (measure != null && !measure.getValues().isEmpty()) {
                addAcumToMeasure(measure, idStation, PRECIPITATION_CODE);
                listMeasures.add(measure);
            } else {
                MeasureResult precipitacion = mapper.getAcumVariable(idStation, Measure.Type.last.toString(),
                        PRECIPITATION_CODE);
                listMeasures.add(precipitacion);
            }
        }

        if (codes.contains(EVAP_CODE)) {
            excludedIds.add(EVAP_CODE);
            MeasureResult measure = mapper.getAcumVariable(idStation, Measure.Type.last.toString(), EVAP_CODE);
            addAcumToMeasure(measure, idStation, EVAP_CODE);
            if (measure != null && !measure.getValues().isEmpty()) {
                listMeasures.add(measure);
            }
        }

        // Actual
        List<MeasureResult> measures = mapper
                .getMeasures(idStation, codes, excludedIds, Measure.Type.actual.toString());

        if (measures == null || measures.isEmpty() || isEmptySetOfMeasures(measures)) {
            logger.warn("No se encontraron medidas actuales, buscando últimas medidas reportadas");
            measures = mapper.getMeasures(idStation, codes, excludedIds, Measure.Type.last.toString());
        }
        listMeasures.addAll(measures);

        // Ordenamos las medidas
        Collections.sort(listMeasures);

        if (gralInfo) {
            Integer lastUpdate = mapper.getLastUpdate(idStation);
            result = factory.create(listMeasures, lastUpdate);
        } else {
            result = factory.create(listMeasures, null);
        }
        return result;
    }

    /**
     * Agrega los valores acumulados de la medida a el array values
     * 
     * @param measure
     *            resultado de lo acumulado en el dia
     */
    private void addAcumToMeasure(MeasureResult measure, Integer idStation, String code) {
        MeasureResult acum24 = mapper.getAcumVariable(idStation, Measure.Type.acum_24.toString(), code);
        if (!acum24.getValues().isEmpty()) {
            Measure medida = acum24.getValues().get(0);
            measure.getValues().add(medida);
        }
        MeasureResult acumWeek = mapper.getAcumVariable(idStation, Measure.Type.acum_week.toString(), code);
        if (!acumWeek.getValues().isEmpty()) {
            Measure medida = acumWeek.getValues().get(0);
            measure.getValues().add(medida);
        }
    }

    /**
     * Valida si es un set de medidas sin valores actuales
     * 
     * @param measures
     *            lista de medidas
     * @return true si es vacio, false otro caso
     */
    private boolean isEmptySetOfMeasures(List<MeasureResult> measures) {
        for (MeasureResult measureResult : measures) {
            if (measureResult.getValues() != null) {
                if (!measureResult.getValues().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Obtiene resumen anual, mensual y diario de las medidas
     * 
     * @param idSation
     *            id de la estacion
     * @return resumen de medidas
     */
    public ActualMeasuresResult getSummaryMeasures(Integer idStation) {

        List<String> codes = mapper.getEnableVariables(idStation);

        ActualMeasuresResult measures = getActualMeasures(idStation, codes, false);

        List<MeasureResult> listMensual = getMeasuresByType(idStation, Measure.Type.month, codes);

        List<MeasureResult> listAnual = getMeasuresByType(idStation, Measure.Type.anual, codes);

        listMensual.addAll(listAnual);

        Map<Variable, List<Measure>> map = constructMap(listMensual);

        for (MeasureResult measureResult : measures.getEntries()) {
            Variable v = measureResult.getVariable();
            List<Measure> listMeasures = map.get(v);
            if (listMeasures != null) {
                measureResult.getValues().addAll(listMeasures);
            }
        }
        return measures;
    }

    /**
     * Obtiene las medidas por tipo y codigos
     * 
     * @param idStation
     *            id de la estacion
     * @param type
     *            tipo {actual, month, anual, last}
     * @param codes
     *            codigos de las variables a buscar
     * @return lista de medidas
     */
    private List<MeasureResult> getMeasuresByType(Integer idStation, Measure.Type type, List<String> codes) {
        List<String> exCodes = new ArrayList<String>();
        List<MeasureResult> list = new ArrayList<MeasureResult>();
        if (codes != null && codes.contains(PRECIPITATION_CODE)) {
            exCodes.add(PRECIPITATION_CODE);
            list.add(mapper.getAcumVariable(idStation, type.toString(), PRECIPITATION_CODE));
        }
        list.addAll(mapper.getMeasures(idStation, codes, exCodes, type.toString()));
        return list;
    }

    /**
     * Obtiene medidas según tipo
     * 
     * @param idStation
     *            id de la estacion
     * @param type
     *            tipo (anual, mensual o diario)
     * @param codes
     *            codigo de las variables a consultar
     * @param date
     *            fecha a consultar
     * @return lista de medidas
     */
    public ActualMeasuresResult getSummaryMeasuresByType(Integer idStation, Measure.Type type, List<String> codes,
            Date date) {
        ActualMeasuresResult measures = null;
        if (codes == null || codes.isEmpty()) {
            codes = mapper.getEnableVariables(idStation);
        }
        // Search by Type
        List<MeasureResult> listMeasures = mapper.getMeasuresByDate(idStation, codes, type.toString(), date);
        // Ordenamos las medidas
        Collections.sort(listMeasures);
        if (listMeasures != null) {
            measures = factory.create(listMeasures, null);
        } else {
            logger.error(String
                    .format("No hay informacion disponible para variables con codes [%s], tipo de medidas [%s] , día [%s] y estación [%s]",
                            codes, type, date, idStation));
        }
        return measures;
    }

    /**
     * Construye un mapa con las variables y medidas
     * 
     * @param list
     *            lista de medidas
     * @return mapa separado por variables
     */
    private Map<Variable, List<Measure>> constructMap(List<MeasureResult> list) {
        Map<Variable, List<Measure>> map = new HashMap<Variable, List<Measure>>();
        for (MeasureResult measureResult : list) {
            List<Measure> measures = measureResult.getValues();
            Variable var = measureResult.getVariable();
            if (map.get(var) == null) {
                map.put(var, measures);
            } else {
                map.get(var).addAll(measures);
            }
        }
        return map;
    }

    /**
     * Busca las medidas historicas para una determinada variable en una
     * determinada fecha
     * 
     * @param idStation
     *            id de la estacion
     * @param codes
     *            id de la variables
     * @param init
     *            fecha inicio
     * @param end
     *            fecha fin
     * @return medidas
     */
    public ActualMeasuresResult getHistoricalData(Integer idStation, List<String> codes, Date init, Date end) {
        List<MeasureResult> setMeasures = new ArrayList<MeasureResult>();
        List<MeasureResult> result = mapper.getHistoricalData(idStation, codes, init, end);
        setMeasures.addAll(result);
        return factory.create(setMeasures, null);
    }
}
