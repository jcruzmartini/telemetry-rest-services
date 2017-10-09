package com.techner.tau.services.service;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.guice.transactional.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.services.config.Config;
import com.techner.tau.services.mapper.OperationMapper;

public class OperationService {

    /** slf4j logger */
    private static Logger logger = LoggerFactory.getLogger(OperationService.class);
    /** mapper */
    private final OperationMapper mapper;
    /**list of operation allowed*/
    private final Set<String> operations;
    /**station service**/
    private final StationService service;

    /**
     * @param userMapper
     */
    @Inject
    public OperationService(OperationMapper mapper, Config config, StationService service) {
        this.mapper = mapper;
        this.operations = config.getAllowedOperations();
        this.service = service;
    }

    @Transactional
    public int insertOperation(Integer idStation, String operation, boolean force) {
        if (!isOperationAllowed(operation)) {
            logger.error("Error insertando la operación {} operacion no permitida", operation);
            return 0;
        }
        if (!service.isValidStation(idStation)){
            logger.error("Error insertando la operación {}. Estacion no valida {}", operation, idStation);
            return 0;
        }
        int i = mapper.insertSMS(operation, force);
        if (i == 1) {
            i = mapper.insertOperation(idStation);
        }
        if (i == 0) {
            logger.error("Error insertando la operación {} p la estación {}", operation, idStation);
        }
        return i;
    }

    /**
     * Lista de operaciones permitidas
     * 
     * @param operation
     *            operaciones
     * @return
     */
    private boolean isOperationAllowed(String operation) {
        if (!StringUtils.isEmpty(operation)) {
            for (String ope : operations) {
                if (operation.startsWith(ope)) {
                    return true;
                }
            }
        }
        return false;
    }

}
