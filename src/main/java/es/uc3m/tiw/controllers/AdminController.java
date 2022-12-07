
package es.uc3m.tiw.controllers;

import es.uc3m.tiw.domains.Event;
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
	public String Usuarios(){
		return "Usuarios";
	}

	@RequestMapping (value = "pagina-eventos")
	public String Eventos(){
		return "Eventos";
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

	@RequestMapping (value = "pagina-usuario/{name}", method = RequestMethod.GET)
	public String returnUsuarios(Model model, @PathVariable String name) {
		
		User us = restTemplate.getForObject("http://localhost:11010/users/{name}", User.class, name);
		model.addAttribute("usuario", us);
		return "viewUsuarios";
		
	}
	
	@RequestMapping (value = "pagina-todos-usuarios", method = RequestMethod.GET)
	public String returnTodosUsuarios(Model model) {
		User[] listaUs = restTemplate.getForObject("http://localhost:11010/users", User[].class);
		model.addAttribute("userList", listaUs);
		return "Read - Usuarios";
	}
	
	
	@RequestMapping (value = "pagina-post-usuario", method = RequestMethod.POST)
	public String saveUser(Model model, @ModelAttribute User us) {
		User newUser = restTemplate.postForObject("http://localhost:11010/users", us, User.class);
		model.addAttribute("usuario", newUser);
		return "viewUsuarios";
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
		return "viewUpdateUsuario";
		
	}
	
	@RequestMapping (value = "pagina-update-usuario", method = RequestMethod.POST)
	public String updateUser(Model model, @ModelAttribute User us){
		Long id = us.getIduser();
		restTemplate.put("http://localhost:11010/users/" + id, us, User.class);
		return "index";	
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

	@RequestMapping (value = "pagina-evento/{name}", method = RequestMethod.GET)
	public String returnEventos(Model model, @PathVariable String name) {

		Event ev = restTemplate.getForObject("http://localhost:11020/events/{name}", Event.class, name);
		model.addAttribute("evento", ev);
		return "viewEventos";

	}

	@RequestMapping (value = "pagina-todos-eventos", method = RequestMethod.GET)
	public String returnTodosEventos(Model model) {
		Event[] listaEv = restTemplate.getForObject("http://localhost:11020/events", Event[].class);
		model.addAttribute("eventList", listaEv);
		model.addAttribute("imgUtil", new ImageUtil());
		return "Read - Eventos";
	}

	@RequestMapping (value = "pagina-post-evento", method = RequestMethod.POST)
	public String saveEvent(Model model, @ModelAttribute Event ev, @RequestParam("photo") MultipartFile filePart)
	throws IOException {
		byte[] data = new byte[(int) filePart.getSize()];
		filePart.getInputStream().read(data, 0, data.length);
		ev.setImage(data);

		Event newEvent = restTemplate.postForObject("http://localhost:11020/events", ev, Event.class);
		model.addAttribute("evento", newEvent);
		return "viewEventos";
	}

	@RequestMapping (value = "pagina-delete-evento", method = RequestMethod.POST)
	public String deleteEvent(Model model, @RequestParam String name){
		Event delEvent = restTemplate.getForObject("http://localhost:11020/events/{name}", Event.class, name);
		if (delEvent != null) {
			restTemplate.delete("http://localhost:11020/events/{id}", delEvent.getIdevent());
		}
		return "index";
	}

	@RequestMapping (value = "pagina-search-evento", method = RequestMethod.POST)
	public String searchEventos(Model model, @RequestParam String name) {

		Event ev = restTemplate.getForObject("http://localhost:11020/events/{name}", Event.class, name);
		model.addAttribute("evento", ev);
		return "viewUpdateEvento";

	}

	@RequestMapping (value = "pagina-update-evento", method = RequestMethod.POST)
	public String updateEvent(Model model, @ModelAttribute Event ev){
		System.out.println(ev.getName());
		System.out.println(ev.getCategory());
		System.out.println(ev.getIdevent());
		Long id = ev.getIdevent();
		restTemplate.put("http://localhost:11020/events/" + id, ev, Event.class);
		return "index";
	}
	
			
}
