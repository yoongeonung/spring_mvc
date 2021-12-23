package jp.ac.hal.yoongeonung.spring_mvc_practice1.web.basic;

import jp.ac.hal.yoongeonung.spring_mvc_practice1.domain.Item;
import jp.ac.hal.yoongeonung.spring_mvc_practice1.domain.ItemParamDTO;
import jp.ac.hal.yoongeonung.spring_mvc_practice1.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String save(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam int quantity,
            Model model
    ) {
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String saveV2(@ModelAttribute ItemParamDTO params, Model model) {
        Item item = new Item(params.getItemName(), params.getPrice(), params.getQuantity());
        Item savedItem = itemRepository.save(item);
        model.addAttribute("item", savedItem);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String saveV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/basic/item";
    }

//    @PostMapping("/add")
    public String saveV4(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String saveV5(@ModelAttribute Item item,
                         RedirectAttributes redirectAttributes
    ) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        // 추가하려는 속성이 return문 안에 없을시 자동적으로 경로변수(path variable) 형식으로 추가한다 -> ?status=true
        // 또한 URL 인코딩또한 해준다.
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute ItemParamDTO param) {
        itemRepository.update(itemId, param);
        return "redirect:/basic/items/{itemId}";
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("Item1", 2000, 100));
        itemRepository.save(new Item("Item2", 1000, 200));
    }
}