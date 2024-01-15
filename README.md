<p align="center">
  <img src="https://raw.githubusercontent.com/PKief/vscode-material-icon-theme/ec559a9f6bfd399b82bb44393651661b08aaf7ba/icons/folder-markdown-open.svg" width="100" />
</p>
<p align="center">
    <h1 align="center">DEIZZAD</h1>
</p>
<p align="center">
    <em>RPG GAME
For more information check: https://httpstatuses.com/404</em>
</p>
<p align="center">
	<img src="https://img.shields.io/github/license/DjoDeSavoie/DEIZZAD.git?style=flat-square&color=0080ff" alt="license">
	<img src="https://img.shields.io/github/last-commit/DjoDeSavoie/DEIZZAD.git?style=flat-square&color=0080ff" alt="last-commit">
	<img src="https://img.shields.io/github/languages/top/DjoDeSavoie/DEIZZAD.git?style=flat-square&color=0080ff" alt="repo-top-language">
	<img src="https://img.shields.io/github/languages/count/DjoDeSavoie/DEIZZAD.git?style=flat-square&color=0080ff" alt="repo-language-count">
<p>
<p align="center">
		<em>Developed with the software and tools below.</em>
</p>
<p align="center">
	<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=flat-square&logo=openjdk&logoColor=white" alt="java">
</p>
<hr>

## 🔗 Quick Links

> - [📍 Overview](#-overview)
> - [📦 Features](#-features)
> - [📂 Repository Structure](#-repository-structure)
> - [🧩 Modules](#-modules)
> - [🚀 Getting Started](#-getting-started)
>   - [⚙️ Installation](#️-installation)
>   - [🤖 Running DEIZZAD](#-running-DEIZZAD)
>   - [🧪 Tests](#-tests)
> - [🛠 Project Roadmap](#-project-roadmap)
> - [🤝 Contributing](#-contributing)
> - [📄 License](#-license)
> - [👏 Acknowledgments](#-acknowledgments)

---

## 📍 Overview

DEIZZAD est un projet de jeu développé en Java, mettant en œuvre des fonctionnalités de déplacement, d'interaction avec le niveau, et d'intelligence artificielle pour les ennemis.

---

## 📦 Features

DEIZZAD, en tant que jeu RPG, propose plusieurs fonctionnalités captivantes. Voici un aperçu des principales caractéristiques du jeu :

Gamestates et Transitions fluides:

Le jeu gère différents états de jeu, tels que le menu, le jeu en cours, et l'écran de fin de partie.
Les transitions entre ces états sont gérées de manière fluide, offrant une expérience utilisateur immersive.
Gestion d'Entités:

Le jeu comprend une hiérarchie d'entités, telles que le joueur, les ennemis, et d'autres objets.
Un gestionnaire d'entités coordonne les interactions et le rendu de ces objets dans le jeu.
Contrôles Clavier et Souris:

Des contrôles clavier et souris sont intégrés pour permettre aux joueurs d'interagir avec le jeu.
Le fichier KeyboardInputs.java semble contenir des fonctionnalités liées aux entrées clavier.
Niveaux et Gestionnaire de Niveaux:

Le jeu comporte différents niveaux, gérés par un LevelManager.java.
Chaque niveau peut avoir ses propres défis et objectifs.
Objets et Gestionnaire d'Objets:

Des objets tels que les potions sont présents dans le jeu, gérés par un ObjectManager.java.
Interface Utilisateur (UI):

Une interface utilisateur est intégrée, comprenant des éléments tels que des boutons, comme on peut le voir dans ImgButton.java.
Utilitaires et Méthodes d'Aide:

Des utilitaires, tels que Constants.java et HelpMethods.java, sont utilisés pour simplifier le code et améliorer la lisibilité.
Ces fonctionnalités combinées offrent une expérience de jeu complète et engageante. Pour plus de détails sur chaque fonctionnalité, consultez les fichiers source correspondants dans le référentiel GitHub.
---

## 📂 Repository Structure

```sh
└── DEIZZAD/
    └── src
        ├── Gamestates
        │   ├── GameOverOverlay.java
        │   ├── Gamestate.java
        │   ├── Menu.java
        │   ├── Playing.java
        │   ├── State.java
        │   └── Statemethods.java
        ├── entities
        │   ├── Enemy.java
        │   ├── EnemyManager.java
        │   ├── Entity.java
        │   ├── Player.java
        │   └── RedOrc.java
        ├── inputs
        │   ├── KeyboardInputs.java
        │   └── MouseInputs.java
        ├── levels
        │   ├── Level.java
        │   └── LevelManager.java
        ├── main
        │   ├── Game.java
        │   ├── GamePanel.java
        │   ├── GameWindow.java
        │   └── MainClass.java
        ├── objects
        │   ├── GameContainer.java
        │   ├── GameObject.java
        │   ├── ObjectManager.java
        │   └── Potion.java
        ├── ui
        │   ├── EndLevelOverlay.java
        │   ├── ImgButton.java
        │   └── MenuButton.java
        └── utilz
            ├── Constants.java
            ├── HelpMethods.java
            └── LoadSave.java
```

---

## 🧩 Modules

<details closed><summary>src.inputs</summary>

| File                                                                                                         | Summary                                                                                                                                                                                              |
| ---                                                                                                          | ---                                                                                                                                                                                                  |
| [KeyboardInputs.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/inputs/KeyboardInputs.java) | 
| [MouseInputs.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/inputs/MouseInputs.java)       |

</details>

<details closed><summary>src.utilz</summary>

| File                                                                                                  | Summary                                                                                                                                                                                          |
| ---                                                                                                   | ---                                                                                                                                                                                              |
| [Constants.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/utilz/Constants.java)     |
| [LoadSave.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/utilz/LoadSave.java)       
| [HelpMethods.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/utilz/HelpMethods.java) 

</details>

<details closed><summary>src.entities</summary>

| File                                                                                                       | Summary                                                                                                                                                                                              |
| ---                                                                                                        | ---                                                                                                                                                                                                  |
| [RedOrc.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/entities/RedOrc.java)       
| [Player.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/entities/Player.java)       
| [EnemyManager.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/entities/EnemyManager.java) 
| [Enemy.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/entities/Enemy.java)               
| [Entity.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/entities/Entity.java)            

</details>

<details closed><summary>src.objects</summary>

| File                                                                                                        | Summary                                                                                                                                                                                              |
| ---                                                                                                         | ---                                                                                                                                                                                                  |
| [Potion.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/objects/Potion.java)              
| [ObjectManager.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/objects/ObjectManager.java) 
| [GameContainer.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/objects/GameContainer.java) 
| [GameObject.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/objects/GameObject.java)      

</details>

<details closed><summary>src.ui</summary>

| File                                                                                                       | Summary                                                                                                                                                                                           |
| ---                                                                                                        | ---                                                                                                                                                                                               |
| [ImgButton.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/ui/ImgButton.java)             
| [EndLevelOverlay.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/ui/EndLevelOverlay.java) 
| [MenuButton.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/ui/MenuButton.java)           

</details>

<details closed><summary>src.Gamestates</summary>

| File                                                                                                               | Summary                                                                                                                                                                                                   |
| ---                                                                                                                | ---                                                                                                                                                                                                       |
| [Statemethods.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/Gamestates/Statemethods.java)    
| [GameOverOverlay.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/Gamestates/GameOverOverlay.java) 
| [Menu.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/Gamestates/Menu.java)                       
| [State.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/Gamestates/State.java)                   
| [Gamestate.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/Gamestates/Gamestate.java)             
| [Playing.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/Gamestates/Playing.java)                

</details>

<details closed><summary>src.main</summary>

| File                                                                                               | Summary                                                                                                                                                                                        |
| ---                                                                                                | ---                                                                                                                                                                                            |
| [MainClass.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/main/MainClass.java)  
| [GameWindow.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/main/GameWindow.java) 
| [Game.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/main/Game.java)            
| [GamePanel.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/main/GamePanel.java)  

</details>

<details closed><summary>src.levels</summary>

| File                                                                                                     | Summary                                                                                                                                                                                            |
| ---                                                                                                      | ---                                                                                                                                                                                                |
| [LevelManager.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/levels/LevelManager.java) 
| [Level.java](https://github.com/DjoDeSavoie/DEIZZAD.git/blob/master/src/levels/Level.java)               

</details>

---

## 🚀 Getting Started

***Requirements***

Ensure you have the following dependencies installed on your system:

* **Java**

### ⚙️ Installation

1. Clone the DEIZZAD repository:

```sh
git clone https://github.com/DjoDeSavoie/DEIZZAD.git
```

2. Change to the project directory:

```sh
cd DEIZZAD
```

3. Install the dependencies:

```sh
mvn clean install
```

### 🤖 Running DEIZZAD

Use the following command to run DEIZZAD:

```sh
java -jar target/myapp.jar
```

### 🧪 Tests

To execute tests, run:

```sh
mvn test
```

---





## 📄 License

ISSAD Aris
DEGRANGE Jonathan
EZZINE Anass


