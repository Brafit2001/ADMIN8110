
package es.uc3m.tiw.controllers;

import es.uc3m.tiw.domains.Event;
import es.uc3m.tiw.domains.Ticket;
import es.uc3m.tiw.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import es.uc3m.tiw.domains.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Controller
public class AdminController {

	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

	@Autowired
	RestTemplate restTemplate;

	// ------------------------------------------------------------------
	// 							NAVEGACION
	// ------------------------------------------------------------------
	@RequestMapping("/")	
	public String index(){
		return "index";
	}

	@RequestMapping (value = "pagina-usuarios")
	public String Usuarios(Model model){
		User[] listaUs = restTemplate.getForObject("http://localhost:11010/users", User[].class);
		model.addAttribute("userList", listaUs);
		return "Usuarios";
	}

	@RequestMapping (value = "pagina-eventos")
	public String Eventos(Model model){
		Event[] listaEv = restTemplate.getForObject("http://localhost:11020/events", Event[].class);
		model.addAttribute("eventList", listaEv);
		model.addAttribute("imgUtil", new ImageUtil());
		return "Eventos";

	}

	@RequestMapping (value = "pagina-entradas")
	public String Entradas(Model model){
		Ticket[] listaTic = restTemplate.getForObject("http://localhost:11030/tickets", Ticket[].class);
		model.addAttribute("ticketList", listaTic);
		return "Entradas";
	}


	// ------------------------------------------------------------------
	// 							USUARIOS
	// ------------------------------------------------------------------

	@RequestMapping (value = "pagina-crear-usuario", method = RequestMethod.GET)
	public String mostrarElFormularioDelUsuario(Model modelo){
		modelo.addAttribute("usuario", new User());
		return "Create - Usuario";
	}
	@RequestMapping (value = "pagina-borrar-usuario", method = RequestMethod.GET)
	public String mostrarElFormularioBorrarUsuario(){
		return "Delete - Usuario.html";
	}

	@RequestMapping (value = "pagina-update-usuario", method = RequestMethod.GET)
	public String mostrarElFormularioUpdateUsuario(Model modelo){
		modelo.addAttribute("usuario", new User());
		return "Update - Usuario.html";
	}

	@RequestMapping (value = "pagina-post-usuario", method = RequestMethod.POST)
	public String saveUser(Model model, @ModelAttribute User us) {
		restTemplate.postForObject("http://localhost:11010/users", us, User.class);
		return "index";
	}
	
	@RequestMapping (value = "pagina-delete-usuario", method = RequestMethod.POST)
	public String deleteUser(Model model, @RequestParam String username){
		User delUser = restTemplate.getForObject("http://localhost:11010/users/{username}", User.class, username);
		if (delUser != null) {
			restTemplate.delete("http://localhost:11010/users/{id}", delUser.getIduser());
		}
		return "index";
	}

	@RequestMapping (value = "pagina-search-usuario", method = RequestMethod.POST)
	public String searchUsuarios(Model model, @RequestParam String username) {
		User us = restTemplate.getForObject("http://localhost:11010/users/{username}", User.class, username);
		model.addAttribute("usuario", us);
		return "viewUsuarios";
		
	}
	
	@RequestMapping (value = "pagina-update-usuario", method = RequestMethod.POST)
	public String updateUser(Model model, @ModelAttribute User us){
		Long id = us.getIduser();
		restTemplate.put("http://localhost:11010/users/" + id, us, User.class);
		return "Usuarios";
	}

	// ------------------------------------------------------------------
	// 							EVENTOS
	// ------------------------------------------------------------------

	@RequestMapping (value = "pagina-crear-evento", method = RequestMethod.GET)
	public String mostrarElFormularioDelEvento(Model modelo){
		modelo.addAttribute("evento", new Event());
		return "Create - Evento.html";
	}

	@RequestMapping (value = "pagina-borrar-evento", method = RequestMethod.GET)
	public String mostrarElFormularioBorrarEvento(){
		return "Delete - Evento.html";
	}

	@RequestMapping (value = "pagina-update-evento", method = RequestMethod.GET)
	public String mostrarElFormularioUpdateEvento(Model modelo){
		modelo.addAttribute("evento", new Event());
		return "Update - Evento.html";
	}

	@RequestMapping (value = "pagina-post-evento", method = RequestMethod.POST)
	public String saveEvent(Model model, @ModelAttribute Event ev, @RequestParam("photo") MultipartFile filePart)
	throws IOException {
		byte[] data = new byte[(int) filePart.getSize()];
		filePart.getInputStream().read(data, 0, data.length);
		String s = Base64.getEncoder().encodeToString(data);
		ev.setImage(s);

		restTemplate.postForObject("http://localhost:11020/events", ev, Event.class);
		return "index";
	}

	@RequestMapping (value = "pagina-delete-evento", method = RequestMethod.POST)
	public String deleteEvent(Model model, @RequestParam Long id){
		restTemplate.delete("http://localhost:11020/events/{id}", id);
		return "index";
	}

	@RequestMapping (value = "pagina-search-evento", method = RequestMethod.POST)
	public String searchEventos(Model model, @RequestParam Long id) {
		Event ev = restTemplate.getForObject("http://localhost:11020/events/{id}", Event.class, id);
		model.addAttribute("evento", ev);
		System.out.println(ev.getName());
		model.addAttribute("imgUtil", new ImageUtil());
		return "viewEventos";

	}

	@RequestMapping (value = "pagina-update-evento", method = RequestMethod.POST)
	public String updateEvent(Model model, @ModelAttribute Event ev, @RequestParam("photo") MultipartFile filePart)
	throws IOException {
		byte[] data = new byte[(int) filePart.getSize()];
		filePart.getInputStream().read(data, 0, data.length);
		String s = Base64.getEncoder().encodeToString(data);
		ev.setImage(s);
		Long id = ev.getIdevent();
		restTemplate.put("http://localhost:11020/events/" + id, ev, Event.class);
		return "index";
	}

	// ------------------------------------------------------------------
	// 							ENTRADAS
	// ------------------------------------------------------------------

	@RequestMapping (value = "pagina-crear-entrada", method = RequestMethod.GET)
	public String mostrarElFormularioDeLaEntrada(Model modelo){
		modelo.addAttribute("entrada", new Ticket());
		return "Create - Entrada.html";
	}

	@RequestMapping (value = "pagina-borrar-entrada", method = RequestMethod.GET)
	public String mostrarElFormularioBorrarEntrada(){
		return "Delete - Entrada.html";
	}

	@RequestMapping (value = "pagina-update-entrada", method = RequestMethod.GET)
	public String mostrarElFormularioUpdateEntrada(Model modelo){
		modelo.addAttribute("entrada", new Ticket());
		return "Update - Entrada.html";
	}

	@RequestMapping (value = "pagina-entrada/{id}", method = RequestMethod.GET)
	public String returnEntradas(Model model, @PathVariable Long id) {

		Ticket tic = restTemplate.getForObject("http://localhost:11030/tickets/{id}", Ticket.class, id);
		model.addAttribute("entrada", tic);
		return "viewEntradas";

	}

	@RequestMapping (value = "pagina-post-entrada", method = RequestMethod.POST)
	public String saveEntrada(Model model, @ModelAttribute Ticket tic)
	{
		restTemplate.postForObject("http://localhost:11030/tickets", tic, Ticket.class);
		return "index";
	}

	@RequestMapping (value = "pagina-delete-entrada", method = RequestMethod.POST)
	public String deleteEntrada(Model model, @RequestParam Long id){
		restTemplate.delete("http://localhost:11030/tickets/{id}", id);
		return "index";
	}

	@RequestMapping (value = "pagina-search-entrada", method = RequestMethod.POST)
	public String searchEntrada(Model model, @RequestParam Long id) {
		Ticket tic = restTemplate.getForObject("http://localhost:11030/tickets/{id}", Ticket.class, id);
		model.addAttribute("entrada", tic);
		return "viewEntradas";

	}

	@RequestMapping (value = "pagina-update-entrada", method = RequestMethod.POST)
	public String updateEntrada(Model model, @ModelAttribute Ticket tic){
		Long id = tic.getIdticket();
		restTemplate.put("http://localhost:11030/tickets/" + id, tic, Ticket.class);
		return "index";
	}
	
			
}
