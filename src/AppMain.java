import user.*;
import wiki.*;
import feedback.*;
import storage.*;

import java.util.ArrayList;
import java.util.List;

public class AppMain {
    public static void main(String[] args) {
        // Step 1: 加载存储方式（JSON or MySQL）
        DataStorage storage = StorageFactory.getStorage();

        // Step 2: 加载已有页面数据
        List<WikiPage> allPages = storage.loadPages();
        if (allPages == null || allPages.isEmpty()) {
            System.out.println("No pages found. Creating default data...");

            // 创建默认页面和分类
            WikiPage page1 = new WikiPage("Getting Started", "Welcome to Scrollden!");
            Category guideCategory = new Category("Guides");
            guideCategory.addComponent(page1);
            allPages = new ArrayList<>();
            allPages.add(page1);
        }

        // Step 3: 创建用户和观察者
        Admin admin = new Admin("admin1");
        Player player = new Player("player1");

        // 添加反馈观察者
        AnalyticsSystem analytics = new AnalyticsSystem();
        for (WikiPage page : allPages) {
            page.addObserver(analytics);
        }

        // Step 4: 测试反馈
        WikiPage page = allPages.get(0);

        // Step 5: 用户提交建议
        player.postSuggestion(page, "Add images please!");
        admin.viewSuggestions();

        // Step 6: 显示页面
        page.display();

        // Step 7: 保存数据
        storage.savePages(allPages);
    }
}