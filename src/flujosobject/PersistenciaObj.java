package flujosobject;

import flujosdata.Partida;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaObj {
	private File archivo;

	public PersistenciaObj() {
		archivo = new File("partidasObj.dat");
	}

	public void guardar(Partida partida) throws IOException, ClassNotFoundException {

		ArrayList<Partida> partidas = new ArrayList<>();

		if (archivo.length() > 0){
			FileInputStream fos = new FileInputStream(archivo);
			ObjectInputStream ois = new ObjectInputStream(fos);

			while(fos.available()>0){
				partidas.add(leerRegistro(ois));
			}
			fos.close();
			ois.close();
		}
		partidas.add(partida);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.archivo, false));
		for (Partida p: partidas) {
			oos.writeObject(p);
		}
		oos.close();
	}

	public flujosdata.Partida leer(int idJugador) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(archivo);
		ObjectInputStream ois = new ObjectInputStream(fis);
		flujosdata.Partida partida = null;
		boolean encontrado = false;
		while (fis.available() > 0 && !encontrado) {
			partida = leerRegistro(ois);
			if (partida.getIdJudador() == idJugador)
				encontrado = true;
		}
		fis.close();
		ois.close();
		return partida;
	}

	// Ejercicio1
	public List<Partida> leerTodos(int idJugador) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(archivo);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<Partida> partidas = new ArrayList<>();
		Partida partida = null;
		while (fis.available() > 0) {
			partida = leerRegistro(ois);
			if (partida.getIdJudador() == idJugador)
				partidas.add(partida);
		}
		fis.close();
		ois.close();
		return partidas;
	}

	// Ejercicio2
	public flujosdata.Partida leerMejorPuntuacion() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(archivo);
		ObjectInputStream ois = new ObjectInputStream(fis);
		flujosdata.Partida partida = null;
		flujosdata.Partida maxPartida = null;

		while (fis.available() > 0) {
			if (partida == null){
				maxPartida = leerRegistro(ois);
			}
			partida = leerRegistro(ois);
			if (maxPartida.getPuntos()<partida.getPuntos()){
				maxPartida = partida;
			}
		}

		fis.close();
		ois.close();
		return maxPartida;
	}

	// Ejercicio3
	public flujosdata.Partida leerMejorPuntuacion(int idJugador) throws IOException, ClassNotFoundException {
		List<flujosdata.Partida> partidas = leerTodos(idJugador);
		flujosdata.Partida partida = null;
		flujosdata.Partida maxPartida = null;

		for (flujosdata.Partida p :partidas) {
			if (partida == null){
				maxPartida = p;
			}
			partida = p;
			if (maxPartida.getPuntos()<partida.getPuntos()){
				maxPartida = partida;
			}
		}
		return maxPartida;
	}

	private flujosdata.Partida leerRegistro(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		return (Partida) ois.readObject();
	}
}