//***********************************
// FALL22 YOSHII: N-gram probability use
// The input file can be of any format
// ***********************************
//-------------------------------------
// YOUR NAME: ** Brian Ramirez
// Tested on Empress using g++
//-------------------------------------
// add includes here **
#include <iostream>
#include <fstream>
#include <string>
using namespace std;

//structure to hold the probabiltity/value pairs from file
struct Prob{
  string probab;
  float value;
};

Prob prob[39];    //array of Prob structs that will hold all data from file

//this function is used to read the probabilities array
//loops through array until it finds a matching string
//returns the corresponding Prob struct
Prob readProb(string x){
  Prob val; //will hold return value of matching probability
  for(int i=0; i<39; i++){
    if(prob[i].probab == x){
      val = prob[i];
    }
  }
  return val;
}

//this function uses trigram probabilities to produce P(word)
void trigram(string word){
  int size = word.length();
  string probstr;  //will hold entire probability string e.g. "a|aa"
  string curr;  //will hold current letter of the given word
  string prev;  //will hold previous character
  string prev2; //will hold second previous character
  Prob match;   //will hold matching probability struct
  float value;  //will hold final product of probability values

  //loops through entire word
  for(int i=0; i<size; i++){
    //starts with first character
    //meaning previous character is null
    if(i == 0){
      //set curr to first char
      curr = word[0];   

      //form probability string e.g. "a| "
      probstr = curr + "|"; 

      //pass prob string into readProb
      //store result as float
      match = readProb(probstr); 

      //start P(word) equation
      cout << "(" + match.probab + " ) " << match.value;

      //set value to first prob value
      value = match.value;
    }

    //else if it is the second character in the string
    //there is only one previous character
    else if(i == 1){
      //set curr to current char
      curr = word[i];

      //set prev to previous char
      prev = word[i-1];

      //form probability string e.g. "a|a"
      probstr = curr + "|" + prev;

      //pass prob string into readProb function
      //store matching pair
      match = readProb(probstr);

      //continue P(word) equation
      cout << " * (" + match.probab + ") " << match.value;

      //set value equal to product
      value *= match.value;
    }

    //else it is a 3rd or nth character
    else{
      //set curr to current char
      curr = word[i];

      //set prev as previous char
      prev = word[i-1];

      //set prev2 as second previous char
      prev2 = word[i-2];

      //form probability string e.g. "a|aa"
      probstr = curr + "|" + prev2 + prev;

      //pass prob string into readProb function
      //store matching pair
      match = readProb(probstr);

      // continue P(word) equation
      cout << " * (" + match.probab + ") " << match.value;

      // pass prob string into readProb function
      // accumulate product
      value *= match.value;
    }
  }
  //finish the probabilty equation by displaying total product
  cout << " = " << value << endl;
}

// This program uses trigram probabilities to produce P(word) 
int main()
{
  // ** Ask the user for the input file name
  string filename;  // used to hold filename
  cout << "Enter the name of the file: ";
  cin >> filename;
  ifstream fin(filename.c_str(), ios::in);
  // ** Read in the trigram probabilties
  for(int i=0; i<39; i++){
    fin >> prob[i].probab;
    fin >> prob[i].value;
  }
  

  // ** Repeat the following until the user
  //    wants to quit:
  cout << "Type 'quit' to stop program.\n";
  string userIn;  //holds word enterd by user
  while(true){
    // ** Ask the user to enter a word
    cout << "Enter a word: ";
    cin >> userIn;
    if(userIn == "quit") break;
    // ** Show all the probabilities you used
    //    to calculate the P(word)
    // ** Show the P(word)
    trigram(userIn);
    
  }// ** end of loop

  return 0;
} 
