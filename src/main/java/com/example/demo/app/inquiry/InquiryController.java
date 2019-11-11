package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryNotFoundException;
import com.example.demo.service.InquiryServiceImpl;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {
	
	private final InquiryServiceImpl inquiryService;
	
	@Autowired
	public InquiryController(InquiryServiceImpl inquiryService){
		this.inquiryService = inquiryService;
	}
	
	@GetMapping
	public String index(Model model) {
		List<Inquiry> list = null;
		list = inquiryService.getAll();
		
		model.addAttribute("inquiryList", list);
		model.addAttribute("title", "Inquiry Index");
		
		return "inquiry/index";
//		return "inquiry/index_boot";
	}
	
	@GetMapping("/form")
	public String form(InquiryForm inquiryForm, Model model) {
		
		model.addAttribute("title", "Inquiry Form");
		return "inquiry/form";
	}
	
	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "InquiryForm");
		return "inquiry/form";
	}
	
	
	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryForm,
	        BindingResult result,
	        Model model) {

		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			//return "inquiry/form";
			return "inquiry/form_boot";
		}
		model.addAttribute("title", "確認ページ");
		return "inquiry/confirm";
	}
	
	@PostMapping("/complete")
	public String complete(@Validated @ModelAttribute InquiryForm inquiryForm,
	        BindingResult result,
	        Model model,
	        RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			return "inquiry/form";
		}
		
		//要変更　ここでEntityへの詰め替え必要
		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());
		
		inquiryService.save(inquiry);
		redirectAttributes.addFlashAttribute("complete", "Completed!");
		return "redirect:/inquiry/form";
	}
	
}