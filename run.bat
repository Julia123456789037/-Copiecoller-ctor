@echo off
REM Compilation
echo Compilation en cours...
javac -d bin -sourcepath src src\metier\Main.java src\metier\Mot.java src\metier\Plagiat.java src\metier\DetecteurPlagiat.java src\Controleur.java src\ihm\FrameBasique.java src\ihm\PanelBasique.java src\ihm\panelTexte.java

if %ERRORLEVEL% equ 0 (
    echo Compilation reussie !
    echo.
    echo Execution du programme...
    cd bin
    java -cp . Controleur
    cd ..
) else (
    echo Erreur lors de la compilation !
    exit /b 1
)
