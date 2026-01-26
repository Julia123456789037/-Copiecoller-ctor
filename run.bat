@echo off
REM Compilation
echo Compilation en cours...
javac -cp lib\tika-app-3.2.3.jar -d bin -sourcepath app app\src\metier\Main.java app\src\metier\Mot.java app\src\metier\Plagiat.java app\src\metier\DetecteurPlagiat.java app\src\Controleur.java app\src\ihm\FrameBasique.java app\src\ihm\PanelBasique.java app\src\ihm\panelTexte.java

if %ERRORLEVEL% equ 0 (
    echo Compilation reussie !
    echo.
    echo Execution du programme...
    cd bin
    java -cp .;..\lib\tika-app-3.2.3.jar app.src.Controleur
    cd ..
) else (
    echo Erreur lors de la compilation !
    exit /b 1
)
