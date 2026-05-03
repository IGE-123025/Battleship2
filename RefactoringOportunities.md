# Refactoring Opportunities

| Local | Code Smell (Fowler) | Refactoring | Nº Aluno |
|-------|---------------------|-------------|----------|
| Game::fireSingleShot | Long Method | Extract Method | 123011 |
| Game::readEnemyFire | Long Method | Extract Method |  |
| Game::randomEnemyFire | Long Method | Extract Method |  |
| Game | Redundant Initialization | Replace with Constant | 123011|
| Ship | Data Class | Encapsulate Field |110764 |
| Ship | Long Method | Extract Method | 110764 |
| Ship | Speculative Generality | Remove Dead Code | 110764 |
| Jogada | Data Class | Encapsulate Field | 123011 |
| Jogada | Long Parameter List | Introduce Parameter Object | 110764 |
| Tasks | Long Method | Extract Method |110764 |
| Tasks | Switch Statements | Decompose Conditional | 110764 |
| PdfExport | Speculative Generality | Remove Method / Replace with Logging | 123011 |
| Carrack | Speculative Generality | Safe Delete | |
| pom.xml | Inappropriate Intimacy | Replace Dependency / Upgrade Dependency |  |
