package com.techner.tau.services.filter;

import javax.servlet.annotation.WebFilter;

import com.google.inject.servlet.GuiceFilter;

@WebFilter(urlPatterns = {"/*"})
public class ServicesFilter extends GuiceFilter {
}