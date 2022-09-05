package site.metacoding.red.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.mapper.MainView;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.request.boards.WriteDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {

	// @PostMapping("/boards/{id}/delete")
	// @PostMapping("/boards/{id}/update")

	private final HttpSession session;
	private final BoardsDao boardsDao;

	@PostMapping("/boards")
	public String writeBoards(WriteDto writeDto) {

		Users principal = (Users) session.getAttribute("principal");

		if (principal == null) {
			return "redirect:/loginForm";
		}

		boardsDao.insert(writeDto.toEntity(principal.getId()));

		return "redirect:/";
	}

	@GetMapping({ "/", "/boards" })
	public String getBoardList(Model model) {
		List<MainView> boardsList = boardsDao.findAll();
		model.addAttribute("boardsList", boardsList);
		return "boards/main";
	}

	@GetMapping("/boards/{id}")
	public String getBoardList(@PathVariable Integer id) {
		
		return "boards/detail";
	}

	@GetMapping("/boards/writeForm")
	public String writeForm() {
		Users principal = (Users) session.getAttribute("principal");
		if (principal == null) {
			return "redirect:/loginForm";
		}
		 return "borards/writeForm";
	}
}
