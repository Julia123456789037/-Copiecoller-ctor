#!/bin/bash

# Compilation
echo "Compilation en cours..."
javac -cp lib/tika-app-3.2.3.jar -d bin -sourcepath app $(cat compile.list | sed 's|^|app/|')

if [ $? -eq 0 ]; then
    echo "Compilation réussie !"
    echo ""
    echo "Exécution du programme..."
    cd bin
    java -cp .:../lib/tika-app-3.2.3.jar app.src.Controleur
    cd ..
else
    echo "Erreur lors de la compilation !"
    exit 1
fi