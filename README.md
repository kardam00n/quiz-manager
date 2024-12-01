# Quiz Manager
Project made for Object-Oriented Technologies course at AGH University. \
The purpose for this project is to manage quiz results from Object-Oriented Programming course lectures and easily assign different prizes for student based on their performance. \
Students take part in quizzes during lectures, which results are stored in .xlsx files. \
This application allows for importing these files, analyzing responses and assigning prizes based on different assigning strategies.
For example: top  x% students get a prize from category A, the rest get a prize from category B.




## Functionalities:
- Import quiz results from .xlsx files
- Assign prizes based on a strategy
- Display quiz results
- Change prize assigned to a student (the lecturer's decision is final) 
- Manage prize and prize categories
- Export quiz results to .xlsx or .pdf file
- Generate statistics for a quiz (**NOT IMPLENTED IN TIME**)

### Implemented strategies:
- *SPEED* - top x% students with the shortest time get a prize from category A, the rest get a prize from category B
- *CORR_ANS* - students who got x points get a prize from category A, then students who got y points get a prize from category B, etc.

You can change the values and prize categories for both strategies in the configuration menu.

## Technologies:
### Backend:
- Java
- Spring Boot
- Hibernate
- SQLite

### Frontend:
- JavaFX
- Retrofit

### Communication:
- REST API

<br>

# How to run the app:
Our app uses gradle as a build tool.
Tough, it is advised to first run the backend module, then the frontend module.

### TODO: Containerize the app, or improve gradle config to run both modules at once.

## [Screenshots](docs/Screenshots.md)

## [Project Structure](docs/ProjectStructure.md)

## [Changelog](docs/Changelog.md)

## Team
- <span style="font-size: 1.5em;">[Dawid Kardacz](https://github.com/kardam00n)</span>
- <span style="font-size: 1.5em;">[Adam Mężydło](https://github.com/amezydlo)</span>
- <span style="font-size: 1.5em;">[Bartosz Rzepa](https://github.com/brzep)</span>

## Warnings
- Before importing a quiz, make sure to add the prize categories and prizes first
- By default, the application is set to use the `SPEED` strategy, but can be changed in the configuration menu 
- During the import of a quiz, the operation may take a while, so it is recommended to switch between quizzes to be sure


