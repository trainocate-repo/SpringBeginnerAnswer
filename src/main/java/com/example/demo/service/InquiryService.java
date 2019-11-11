package com.example.demo.service;

import java.util.List;

import com.example.demo.app.inquiry.InquiryForm;
import com.example.demo.entity.Inquiry;

public interface InquiryService {
	
	void save(Inquiry inquiry);
	
	void update(Inquiry inquiry);
	
	List<Inquiry> getAll();

}