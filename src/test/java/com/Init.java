package com;

import java.util.Arrays;
import java.util.List;

public class Init {

	private static final int NUM_DAYS_YEAR = 365;
	// Gsc constante solar = 0,082
	private static final float GSC = 0.082f;
	// Coeficiente especifico del cultivo
	private static float COEF_CULTIVO = 0.23f;
	// constante de Stefan-Boltzmann
	private static final float CSBOLTZ = 0.000000005f;
	// calor latente de vaporización, 2,45 [ MJ kg-1],
	private static final float CLV = 2.45f;
	// calor específico a presión constante,
	private static final float CP = 0.001013f;
	// ε cociente del peso molecular de vapor de agua /aire seco = 0,622.
	private static final float CPM = 0.622f;
	// Lista de variables obligatorias
	private final static List<String> CODES = Arrays.asList("T", "H", "VV", "R", "P");

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Double t = 1212.5;
		System.out.println(String.format("%f", t));
		// try {
		// List<MeasureResult> listMeasures = Constraints.constrainedList(new
		// ArrayList<MeasureResult>(), Constraints.notNull());
		// MeasureResult m = null;
		// listMeasures.add(m);
		// } catch (Exception e) {
		// System.err.println(e);
		// }

		// // Corregida a nivel del mar
		// double presionAdj = 101.3 * Math.pow((293 - (0.0065 * 1800)) / 293,
		// 5.26);
		// // constante psicrométrica //ec8
		// double cpsico = (CP * presionAdj) / (CPM * CLV);
		//
		// System.out.println(presionAdj);
		// System.out.println(cpsico);

		// // Informacion de la temperatura obtenida a partir de los servicios
		// float tempMax = 21.5f; // ºC
		// float tempMin = 12.3f; // ºC
		//
		// // Informacion de la humedad obtenida a partir de los servicios
		// float HRMax = 84f; // %
		// float HRMin = 63f; // %
		//
		// // Informacion de la velocidad del viento promedio diaria obtenida a
		// // partir de los
		// // servicios
		// // u2, velocidad de viento promedio diaria a 2 metros de altura
		// float vViento = 2.078f; // m/s Wind
		//
		// // Informacion de la radiacion neta onda corta promedio diaria
		// obtenida
		// // a partir de los servicios
		// // Rs promedio diario
		// float radiacion = 22.07f; // MJ m-2 dia-1
		//
		// // Informacion de la presión diaria obtenida a partir de los
		// servicios
		// // P promedio diario
		// float presion = 100.1f; // KPa
		//
		// // Altitud del lugar donde esta instalada la estacion, viene de la
		// // informacion de la estacion almacenada
		// float altitud = 100; // mts
		// // Numero de día donde 1 es el 1 de Enero y NUM_DAYS_YEAR es el 31 de
		// // Dic
		// int numday = 187;
		// // Calendar cal = Calendar.getInstance();
		// // cal.setTime(new Date());
		// // int numday = cal.get(Calendar.DAY_OF_YEAR);
		// System.out.println("Numero de dia del año.     NuD = " + numday);
		//
		// // Latitud del lugar donde esta instalada la estacion, viene de la
		// // informacion de la estacion almacenada
		// float latitud = 50.80f;
		// // Latitud en Radianes
		// float latitudRad = (float) ((latitud * Math.PI) / 180f);
		//
		// float tempMedia = (tempMax + tempMin) / 2;
		// float tempA = tempMedia + 237.3f;
		//
		// // Pendiente de la curva de presión de saturación de vapor // ec13
		// float pcv = (float) ((4098f * calcularDeficitVapor(tempMedia)) /
		// (Math
		// .pow(tempA, 2)));
		// System.out.println("Pendiente curva de presion.  Δ = " + pcv);
		//
		// // constante psicrométrica //ec8
		// float cpsico = (CP * presion) / (CPM * CLV);
		// System.out.println("Constante psicrométrica.     γ = " + cpsico);
		//
		// float ecp1 = (1 + 0.34f * vViento);
		//
		// // / --------------PARA USAR AL FINAL
		// float ecp11 = (pcv + cpsico * ecp1);
		// float ecpf2 = pcv / ecp11;
		// float ecpf3 = cpsico / ecp11;
		// float ecpf4 = (900f / (tempMedia + 273f)) * vViento;
		// // / ------------- PARA USAR AL FINAL
		//
		// // deficit de vapor max //ec11
		// float dvemax = calcularDeficitVapor(tempMax);
		// // deficit de vapor min //ec11
		// float dvemin = calcularDeficitVapor(tempMin);
		// // deficit de vapor med //ec12
		// float dvemed = (dvemax + dvemin) / 2;
		//
		// // Presión real de vapor (ea) derivada de datos de humedad relativa
		// // //ec17
		// float prv = (dvemin * (HRMax / 100) + dvemax * (HRMin / 100)) / 2;
		//
		// // Déficit de presión de vapor
		// // / --------------PARA USAR AL FINAL
		// float dpv = dvemed - prv;
		// System.out.println("Déficit presión vapor. (es-ea) = " + dpv);
		//
		// float numDayAux = (float) ((2 * Math.PI * numday) / NUM_DAYS_YEAR);
		//
		// // La distancia relativa inversa Tierra-Sol, dr
		// float dr = (float) (1 + 0.033 * Math.cos(numDayAux));
		// System.out.println("D. rel. inv. tierra-sol.  (dr) = " + dr);
		//
		// // Decinacion solar δ
		// float decSol = (float) (0.409 * Math.sin(numDayAux - 1.39f));
		// System.out.println("Declinación solar.           δ = " + decSol);
		//
		// // Angulo de radiacion a la puesta del sol (ws)
		// float raSin = (float) (Math.sin(latitudRad) * Math.sin(decSol));
		// float raCos = (float) (Math.cos(latitudRad) * Math.cos(decSol));
		// float raTan = (float) (-Math.tan(latitudRad) * Math.tan(decSol));
		//
		// float aRadPSol = (float) Math.acos(raTan);
		// System.out.println("Angulo rad puesta del sol (ws) = " + aRadPSol);
		//
		// // Radiación extraterrestre para periodos diarios (Ra)
		// float ra = (float) (((24 * 60) / Math.PI) * GSC * dr * (aRadPSol
		// * raSin + raCos * Math.sin(aRadPSol)));
		// System.out.println("Rad. extraterrestre.      (Ra) = " + ra);
		//
		// // Radiacion dia despejado //ec37
		// float rso = ra * (0.75f + 2 * 0.00002f * altitud);
		// System.out.println("Rad. dia despejado.      (Rso) = " + rso);
		//
		// // Radiacion neta solar onda corta Rns
		// float rns = (1f - COEF_CULTIVO) * radiacion;
		// System.out.println("Rad. Neta onda corta.    (Rns) = " + rns);
		//
		// // Radiacion neta solar onda larga Rnl part1 //ec39
		// float rnlp1 = (float) (CSBOLTZ * ((Math.pow((tempMax + 273.16f), 4) +
		// Math
		// .pow((tempMin + 273.16f), 4)) / 2));
		// // Radiacion neta solar onda larga Rnl part2 //ec39
		// float rnlp2 = (float) (0.34f - 0.14f * Math.sqrt(prv));
		// // Radiacion neta solar onda larga Rnl part3 //ec39
		// float rnlp3 = 1.35f * (radiacion / rso) - 0.35f;
		//
		// // Radiacion neta solar onda larga Rnl Final //ec39
		// float rnl = rnlp1 * rnlp2 * rnlp3;
		// System.out.println("Rad. Neta onda larga.    (Rnl) = " + rnl);
		//
		// // Radiacion neta //ec40
		// float rn = rns - rnl;
		// System.out.println("Rad. Neta.                (Rn) = " + rn);
		//
		// // Flujo del calor del suelo, es 0 cuando se usan datos diarios
		// float G = 0f;
		//
		// // / --------------PARA USAR AL FINAL
		// float ecpf5 = 0.408f * (rn - G);
		// float ecpf6 = ecpf5 * ecpf2;
		// float ecpf7 = ecpf4 * dpv * ecpf3;
		// float ETO = ecpf6 + ecpf7;
		//
		// System.out.println("Evapotraspiración total.   ETO = " + ETO);
	}

	private static float calcularDeficitVapor(float tempMedia) {
		float tempA = tempMedia + 237.3f;
		return (float) (0.6108 * Math.exp(((17.27 * tempMedia) / tempA)));
	}

}
