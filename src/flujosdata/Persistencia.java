package flujosdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Persistencia {
	private File archivo;

	public Persistencia() {
		archivo = new File("partidas.dat");
	}

	public void guardar(Partida partida) throws IOException {
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo, true));
		dos.writeInt(partida.getIdJudador());
		dos.writeUTF(partida.getNomJugador());
		dos.writeLong(partida.getPuntos());
		dos.writeDouble(partida.getTiempo());
		dos.close();
	}

	public Partida leer(int idJugador) throws IOException {
		FileInputStream fis = new FileInputStream(archivo);
		DataInputStream dis = new DataInputStream(fis);
		Partida partida = null;
		boolean encontrado = false;
		while (fis.available() > 0 && !encontrado) {
			partida = leerRegistro(dis);
			if (partida.getIdJudador() == idJugador)
				encontrado = true;
		}
		fis.close();
		dis.close();
		return partida;
	}

	// Ejercicio1
	public List<Partida> leerTodos(int idJugador) throws IOException {
		FileInputStream fis = new FileInputStream(archivo);
		DataInputStream dis = new DataInputStream(fis);
		ArrayList<Partida> partidas = new ArrayList<>();
		Partida partida = null;
		while (fis.available() > 0) {
			partida = leerRegistro(dis);
			if (partida.getIdJudador() == idJugador)
				partidas.add(partida);
		}
		fis.close();
		dis.close();
		return partidas;
	}

	// Ejercicio2
	public Partida leerMejorPuntuacion() throws IOException {
		FileInputStream fis = new FileInputStream(archivo);
		DataInputStream dis = new DataInputStream(fis);
		Partida partida = null;
		Partida maxPartida = null;

		while (fis.available() > 0) {
			if (partida == null){
				maxPartida = leerRegistro(dis);
			}
			partida = leerRegistro(dis);
			if (maxPartida.getPuntos()<partida.getPuntos()){
				maxPartida = partida;
			}
		}

		fis.close();
		dis.close();
		return maxPartida;
	}

	// Ejercicio3
	public Partida leerMejorPuntuacion(int idJugador) throws IOException {
		List<Partida> partidas = leerTodos(idJugador);
		Partida partida = null;
		Partida maxPartida = null;

		for (Partida p :partidas) {
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

	private Partida leerRegistro(DataInputStream dis) throws IOException {
		Partida partida = new Partida();
		partida.setIdJudador(dis.readInt());
		partida.setNomJugador(dis.readUTF());
		partida.setPuntos(dis.readLong());
		partida.setTiempo(dis.readDouble());
		return partida;
	}

}