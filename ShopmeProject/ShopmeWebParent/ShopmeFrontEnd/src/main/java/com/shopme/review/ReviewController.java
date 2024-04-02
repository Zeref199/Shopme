package com.shopme.review;

import com.shopme.ControllerHelper;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Review;
import com.shopme.common.exception.ReviewNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ReviewController {
    private final String defaultRedirectURL = "redirect:/reviews/page/1?sortField=reviewTime&sortDir=desc";
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ControllerHelper controllerHelper;

    @GetMapping("/reviews")
    public String listFirstPage(Model model) {
        return defaultRedirectURL;
    }

    @GetMapping("/reviews/page/{pageNum}")
    public String listReviewsByCustomerByPage(Model model, HttpServletRequest request,
                                              @PathVariable(name = "pageNum") int pageNum,
                                              String keyword, String sortField, String sortDir) {
        Customer customer = controllerHelper.getAuthenticatedCustomer(request);
        Page<Review> page = reviewService.listByCustomerByPage(customer, keyword, pageNum, sortField, sortDir);
        List<Review> listReviews = page.getContent();

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("moduleURL", "/reviews");

        model.addAttribute("listReviews", listReviews);

        long startCount = (long) (pageNum - 1) * ReviewService.REVIEWS_PER_PAGE + 1;
        model.addAttribute("startCount", startCount);

        long endCount = startCount + ReviewService.REVIEWS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        model.addAttribute("endCount", endCount);

        return "reviews/reviews_customer";
    }

    @GetMapping("/reviews/detail/{id}")
    public String viewReview(@PathVariable("id") Integer id, Model model,
                             RedirectAttributes ra, HttpServletRequest request) {
        Customer customer = controllerHelper.getAuthenticatedCustomer(request);
        try {
            Review review = reviewService.getByCustomerAndId(customer, id);
            model.addAttribute("review", review);

            return "reviews/review_detail_modal";
        } catch (ReviewNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return defaultRedirectURL;
        }
    }
}