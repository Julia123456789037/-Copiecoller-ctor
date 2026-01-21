#!/bin/bash

# Compilation
echo "Compilation en cours..."
javac -d bin -sourcepath app $(cat compile.list | sed 's|^|app/|')

if [ $? -eq 0 ]; then
    echo "Compilation réussie !"
    echo ""
    echo "Exécution du programme..."
    cd bin
    java -cp . Controleur
    cd ..
else
    echo "Erreur lors de la compilation !"
    exit 1
fi
