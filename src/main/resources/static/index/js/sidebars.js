/* global bootstrap: false */
(() => {
  'use strict'
  const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
  tooltipTriggerList.forEach(tooltipTriggerEl => {
    new bootstrap.Tooltip(tooltipTriggerEl)
  })
})()


document.querySelectorAll(".nav-link").forEach((link) => {
    if (link.href === window.location.href) {
		link.classList.remove("link-body-emphasis");
        link.classList.add("active");
        link.setAttribute("aria-current", "page");
    }
});