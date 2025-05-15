/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package pokemonquiz;

/**
 *  Harold Stevens
 *  Lab 8 Java ArrayList and HashMap
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.List;

public class PokemonQuiz extends JFrame {

    private ArrayList<Pokemon> pokemonList = new ArrayList<>();
    private HashMap<String, Pokemon> pokemonMap = new HashMap<>();
    private int currentIndex = 0;
    private int score = 0;
    private int totalQuestions = 0;

    private JLabel imageLabel = new JLabel();
    private JTextField answerField = new JTextField( 15 );
    private JLabel feedbackLabel = new JLabel( " " );
    private JLabel scoreLabel = new JLabel( "Score: 0/0" );

    public PokemonQuiz() {
        setTitle( "Pokémon Quiz Game" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new BorderLayout() );

        // Center Panel: Image
        JPanel centerPanel = new JPanel( new BorderLayout() );
        imageLabel.setHorizontalAlignment( SwingConstants.CENTER );
        centerPanel.add( imageLabel, BorderLayout.CENTER );

        // Bottom Panel: Answer Field and Submit Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.add( new JLabel( "Who's that Pokémon?" ));
        bottomPanel.add( answerField );
        JButton submitButton = new JButton( "Submit" );
        bottomPanel.add( submitButton );

        // Score and feedback
        JPanel topPanel = new JPanel( new BorderLayout() );
        topPanel.add( scoreLabel, BorderLayout.EAST );
        topPanel.add( feedbackLabel, BorderLayout.CENTER );

        add( topPanel, BorderLayout.NORTH );
        add( centerPanel, BorderLayout.CENTER );
        add( bottomPanel, BorderLayout.SOUTH );

        // Load data
        loadFile("src/Resources/pokemon.csv");
        nextQuestion();

        // Submit answer action
        submitButton.addActionListener( e -> checkAnswer() );
        answerField.addActionListener( e -> checkAnswer() );

        setSize(400, 500);
        setLocationRelativeTo( null );
        setVisible( true );
    }

    private void loadFile( String filename ) {
        try {
            List<String> lines = Files.readAllLines( Paths.get( filename ));
            boolean firstLine = true;
            for ( String line : lines ) {
                if ( firstLine ) { firstLine = false; continue; }
                String[] fields = splitCsvLine( line );
                if ( fields.length < 10 ) continue;
                int number = Integer.parseInt( fields[0] );
                String name = fields[1];
                String type1 = fields[2];
                String type2 = fields[3];
                int hp = Integer.parseInt( fields[4] );
                int attack = Integer.parseInt( fields[5] );
                int defense = Integer.parseInt( fields[6] );
                int speed = Integer.parseInt( fields[7] );
                String imageUrl = fields[8];
                String description = fields[9];
                Pokemon p = new Pokemon( number, name, type1, type2, hp, attack, defense, speed, imageUrl, description );
                pokemonList.add(p);
                pokemonMap.put( name.toLowerCase(), p );
            }
        } catch ( IOException e ) {
            JOptionPane.showMessageDialog( this, "Error loading file: " + e.getMessage() );
        }
    }

    // Helper to split CSV line (handles commas inside quoted fields)
    private String[] splitCsvLine( String line ) {
        ArrayList<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for ( char c : line.toCharArray() ) {
            if ( c == '"' ) {
                inQuotes = !inQuotes;
            } else if ( c == ',' && !inQuotes ) {
                tokens.add( sb.toString() );
                sb.setLength( 0 );
            } else {
                sb.append( c );
            }
        }
        tokens.add( sb.toString() );
        return tokens.toArray( new String[0] );
    }

    private void nextQuestion() {
        if ( pokemonList.isEmpty() ) return;
        currentIndex = new Random().nextInt( pokemonList.size() );
        Pokemon p = pokemonList.get( currentIndex );
        try {
            ImageIcon icon = new ImageIcon( new URL( p.getImageUrl() ));
            Image img = icon.getImage().getScaledInstance( 200, 200, Image.SCALE_SMOOTH );
            imageLabel.setIcon( new ImageIcon( img ));
        } catch ( Exception e ) {
            imageLabel.setIcon( null );
        }
        answerField.setText( "" );
        feedbackLabel.setText( " " );
        totalQuestions++;
        scoreLabel.setText( "Score: " + score + "/" + ( totalQuestions - 1 ));
    }

    private void checkAnswer() {
        String userAnswer = answerField.getText().trim().toLowerCase();
        Pokemon currentPokemon = pokemonList.get( currentIndex );
        if ( userAnswer.equals( currentPokemon.getName().toLowerCase() )) {
            score++;
            feedbackLabel.setText( "Correct! " + currentPokemon.getName() );
        } else {
            feedbackLabel.setText( "Incorrect! It was " + currentPokemon.getName() );
        }
        scoreLabel.setText( "Score: " + score + "/" + totalQuestions );
        // Next question after short delay
        javax.swing.Timer timer = new javax.swing.Timer( 1200, e -> nextQuestion() );  // I had trouble with this line since I guess both java.swing and java.util have a Timer class you can call
        timer.setRepeats( false );
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater( PokemonQuiz::new );
    }
}
