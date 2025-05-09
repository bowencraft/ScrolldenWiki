import user.*;
import wiki.*;
import feedback.*;
import storage.*;

import gui.AdminGUI;
import gui.PlayerGUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AppMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
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

            // Step 4: 测试反馈（可选）
            // WikiPage page = allPages.get(0);
            // player.postSuggestion(page, "Add images please!");
            // admin.viewSuggestions();
            // page.display();

            // Step 5: 启动GUI界面，选择用户类型
            String[] options = {"Admin", "Player"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Choose user type:",
                "ScrolldenWiki",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (choice == 0) {
                // 启动管理员界面
                new AdminGUI(admin, allPages, storage);
            } else {
                // 启动玩家界面
                new PlayerGUI(player, allPages, storage);
            }

            // Step 6: 保存数据（退出时调用或可用菜单触发）
            // storage.savePages(allPages);
        });
    }
}
