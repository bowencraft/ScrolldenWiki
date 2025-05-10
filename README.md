# ScrolldenWiki

### **Project Structure Overview**

```bat
SCROLLDENWIKI/
│
├── data/                               # Data files in YAML format
│   ├── suggestions.yml                 # Stores player-submitted suggestions
│   └── wiki_pages.yml                  # Stores all wiki page content
│
├── graphs/                             # Diagrams and PlantUML source files
│
├── src/                                
│   ├── config/                         # Reserved for future configuration classes
│   ├── data/                           # YAML file access and raw data handling
│   ├── gui/                            # All graphical user interface components
│   │   ├── AdminGUI.java
│   │   ├── PlayerGUI.java
│   │   ├── HomePageGUI.java
│   │   ├── PageBrowserGUI.java
│   │   ├── PageBrowserPlayerGUI.java
│   │   ├── PageEditorGUI.java
│   │   ├── WikiPageManagerGUI.java
│   │   ├── AnalyticsViewerGUI.java
│   │   ├── SuggestionModifierGUI.java
│   │   └── SuggestionViewerGUI.java
│   ├── review/                         # Review model (public player comments)
│   │   ├── Review.java
│   ├── storage/                        # Data storage logic and strategy
│   │   ├── DataStorage.java
│   │   ├── YamlStorage.java
│   │   └── StorageFactory.java
│   ├── suggestion/                     # Suggestion system classes
│   │   ├── Suggestion.java
│   │   └── SuggestionManager.java
│   ├── user/                           # User hierarchy
│   │   ├── User.java
│   │   ├── Admin.java
│   │   └── Player.java
│   ├── wiki/                           # Wiki page content and structure
│   │   ├── WikiPage.java
│   │   ├── WikiComponent.java
│   │   └── Category.java
│   └── AppMain.java                    # Application entry point (main menu and routing)
│
└── README.md                           
```



------



### **Diagram and Documentation Location**

All diagrams are stored in the graphs/ folder and include:

- All diagrams (Use Case, Class Diagram, Sequence Diagram) are saved in the graphs/ folder.
- The .plantuml files are the source files and can be rendered directly using PlantUML or a VSCode plugin.
- The .png files are the rendered images and can be inserted directly into reports or presentations.

These files support both visualization and documentation of the system architecture and logic flow.



------



### **How to Run the Project**

#### **Option 1: Run in Eclipse IDE**

1. **Import Project**

   Open Eclipse → File → *Import Existing Java Project* → Select the SCROLLDENWIKI/ folder.

2. **Run the Program**

   Locate AppMain.java in src/ → Right-click → *Run As* → *Java Application*



#### **Option 2: Compile and Run via Terminal**

1. Navigate to the src/ directory:

```
cd SCROLLDENWIKI/src
```

2. Compile:

```
javac AppMain.java
```

3. Run:

```
java AppMain
```

