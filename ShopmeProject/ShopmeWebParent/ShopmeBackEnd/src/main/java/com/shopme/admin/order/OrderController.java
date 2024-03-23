package com.shopme.admin.order;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.paging.PagingAndSortingParam;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.setting.SettingService;
import com.shopme.common.entity.Setting;
import com.shopme.common.entity.order.Order;
import com.shopme.common.exception.OrderNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OrderController {
    private final String defaultRedirectURL = "redirect:/orders/page/1?sortField=orderTime&sortDir=desc";

    @Autowired
    private OrderService orderService;
    @Autowired private SettingService settingService;

    @GetMapping("/orders")
    public String listFirstPage() {
        return defaultRedirectURL;
    }

    @GetMapping("/orders/page/{pageNum}")
    public String listByPage(
            @PagingAndSortingParam(listName = "listOrders", moduleURL = "/orders") PagingAndSortingHelper helper,
            @PathVariable(name = "pageNum") int pageNum,
            HttpServletRequest request, @AuthenticationPrincipal ShopmeUserDetails loggedUser) {

        orderService.listByPage(pageNum, helper);
        loadCurrencySetting(request);

        if (!loggedUser.hasRole("Admin") && !loggedUser.hasRole("Salesperson") && loggedUser.hasRole("Shipper")) {
            return "orders/orders_shipper";
        }

        return "orders/orders";
    }

    private void loadCurrencySetting(HttpServletRequest request) {
        List<Setting> currencySettings = settingService.getCurrencySettings();

        for (Setting setting : currencySettings) {
            request.setAttribute(setting.getKey(), setting.getValue());
        }
    }

    @GetMapping("/orders/detail/{id}")
    public String viewOrderDetails(@PathVariable("id") Integer id, Model model,
                                   RedirectAttributes ra, HttpServletRequest request,
                                   @AuthenticationPrincipal ShopmeUserDetails loggedUser) {
        try {
            Order order = orderService.get(id);
            loadCurrencySetting(request);

            boolean isVisibleForAdminOrSalesperson = false;

            if (loggedUser.hasRole("Admin") || loggedUser.hasRole("Salesperson")) {
                isVisibleForAdminOrSalesperson = true;
            }

            model.addAttribute("isVisibleForAdminOrSalesperson", isVisibleForAdminOrSalesperson);
            model.addAttribute("order", order);

            return "orders/order_details_modal";
        } catch (OrderNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return defaultRedirectURL;
        }

    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            orderService.delete(id);;
            ra.addFlashAttribute("message", "The order ID " + id + " has been deleted.");
        } catch (OrderNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
        }

        return defaultRedirectURL;
    }
}
