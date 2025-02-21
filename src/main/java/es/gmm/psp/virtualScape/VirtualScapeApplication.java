package es.gmm.psp.virtualScape;


import es.gmm.psp.virtualScape.Model.Reserva;
import es.gmm.psp.virtualScape.service.ReservaService;
import es.gmm.psp.virtualScape.service.SalaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class VirtualScapeApplication {

	public static void main(String[] args) {

		var context = SpringApplication.run(VirtualScapeApplication.class, args);
		VirtualScapeApplication app = context.getBean(VirtualScapeApplication.class);

		ReservaService reservaService = context.getBean(ReservaService.class);
		SalaService salaService=context.getBean(SalaService.class);

		List<Reserva> reservas=reservaService.findAll();
		System.out.println(reservas);
		Reserva r=reservaService.findById("67b484fd1e8726427b3b727e");
		System.out.println(r);
		/*
		List<String> tematicas=new ArrayList<>();
		tematicas.add("Terror");
		tematicas.add("Inteligencia");
		Sala sala=new Sala("La Casa del Terro",2,6,tematicas);
		salaService.insertarSala(sala);*/


		/*
		Fecha fecha1=new Fecha(20,12);
		Contacto contacto1=new Contacto("Sergio2",6677,5);
		Reserva reserva=new Reserva("La Casa del Terro",fecha1,contacto1);
		reservaService.insertarReserva(reserva);
*/

		/*Sala s=salaService.buscaPorNombre("La Casa del Terro");
		System.out.println(s);*/
	}


}
