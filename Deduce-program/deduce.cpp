#include <fstream>
#include <iostream>
#include <string>
using namespace std;

//  FALL22 Yoshii - HW4 EC for Inferences
//   Given two facts and one implies rule
//   see what you can deduce.
//   Each fact has a true or false value
//   The condition part of the implication rule
//   may use AND or OR.
// Look for ** to complete.
//-----------------------------------------
// YOUR NAME: ** Brian Ramirez
// Tested on Empress using g++
//----------------------------------------

// Each fact is a word e.g. RAINING
// Each fact is followed by either true or false
string facts[2][2];

// a rule
struct rule {
  string p1;    // a condition
  string op;    // op is AND or OR
  string p2;    // the second condition
  string concl; // the conclusion
};

// for the current KB
rule myrule; // the rule
int num;     // number of conditions in the rule

// ========= Functions follow ==============

// check to see if a condition is notted
bool isnotted(string s) {
  if (s[0] == '~')
    return true;
  else
    return false;
}

// read in the contents of a kb file
// and display them
void readin() {
  string kb;
  cout << "Enter the KB name: ";
  cin >> kb;
  ifstream fin(kb.c_str(), ios::in);

  fin >> num; // how many facts or conditions

  //** for each fact
  for (int i = 0; i < num; i++) {
    // ** read the fact word
    fin >> facts[i][0];
    // ** read true or false
    fin >> facts[i][1];
  }

  // ** display the facts
  for (int i = 0; i < num; i++) {
    cout << "Fact " << i + 1 << ": ";
    for (int j = 0; j < 2; j++) {
      cout << facts[i][j] << " ";
    }
    cout << endl;
  }
  string temp = "";    //used to read ahead
  string implies ="";  //used to hold implies symbol
  // ** read the rule condition(s)
  fin >> myrule.p1;
  //read next string
  fin >> temp;
  //look ahead, if it is operator meaning 2 conditions
  if(temp == "AND" || temp == "OR"){
    // ** read the operator
    myrule.op = temp;
    //read in second condition
    fin >> myrule.p2;
    // ** read the implies symbol
    fin >> implies;
    // ** read the conclusion
    fin >> myrule.concl;
    // ** display them
    cout << myrule.p1 << " " << myrule.op << " " << myrule.p2 << " "
    << implies << " " << myrule.concl << endl;
  }
  //else there is only one condition
  else{
    //store implies symbol that was read in
    implies = temp;
    //read in conclusion
    fin >> myrule.concl;
    
    // ** display them
    cout << myrule.p1 << " " << implies << " " << myrule.concl << endl;
  }


} // end of reading the kb

// deduce based on one condition
string deduceOne()
{
  string unnotted = "";  //used to store unnotted equivalent of notted condition
  
  if(isnotted(myrule.p1)){
    unnotted = myrule.p1.substr(1, myrule.p1.size() -1);
  }
  
  // ** check the notted condition vs. false fact
  if(isnotted(myrule.p1) && (facts[0][0] == unnotted) && (facts[0][1] == "false")){
    return myrule.concl;
  }
  // ** check the unnotted condition vs. true fact
  if(!isnotted(myrule.p1) && (facts[0][0] == myrule.p1) && (facts[0][1] == "true")){
    return myrule.concl;
  }
  // ** check the conclusion vs. false fact
  string notSymbol = "~";  //used for appending to condition
  if((myrule.concl == facts[0][0]) && (facts[0][1] == "false")){
    //return appended not symbol to condition
    return notSymbol += myrule.p1;
  }
  
  //else nothing can be deduced
  else
    return "nothing";
}

// deduce based on two conditions
// Think: when is a condition true?
 string deduceTwo()
 {
  bool cond1; //keeps track of the validity of condition1
  bool cond2; //keeps track of valididty of condition2
   
  // ** check to see if the first condition is true
  string unnotted1 = "";  //used to store unnotted equivalent of notted condition
   
  //if first condition is notted then convert to unnotted version
  if(isnotted(myrule.p1)){
    unnotted1 = myrule.p1.substr(1, myrule.p1.size() -1);
  }
  //compare the notted condition to the false fact
  if(isnotted(myrule.p1) && (facts[0][0] == unnotted1) && (facts[0][1] == "false")){
    cond1 = true;
  }
   
  //compare the unnotted condition to the true fact
  else if(!isnotted(myrule.p1) && (facts[0][0] == myrule.p1) && (facts[0][1] == "true")){
    cond1 = true;
  }
  else cond1 = false;
  
  // ** check to see if the second condition is true
  string unnotted2 = "";  //used to store unnotted equivalent of notted condition

  //if first condition is notted then convert to unnotted version
  if(isnotted(myrule.p2)){
    unnotted2 = myrule.p2.substr(1, myrule.p2.size() -1);
  }
  //compare the notted condition to the false fact
  if(isnotted(myrule.p2) && (facts[1][0] == unnotted2) && (facts[1][1] == "false")){
    cond2 = true;
  }
  //else if the 2nd condition is unnotted check against true fact
  else if(!isnotted(myrule.p2) && facts[1][0] == myrule.p2 && facts[1][1] == "true"){
    cond2 = true;
  }
  else cond2 = false;

  // ** check the operator to decide if the conclusion is true
  //first check if operator is an AND then it compares cond1
  //and cond2 to make sure they are BOTH true then returns conclusion
  if((myrule.op == "AND") && (cond1 == true && cond2 == true)){
    return myrule.concl;
  }
  //then check if operator is OR and checks whether either
  //condition is true then returns conclusion
  if((myrule.op == "OR") && (cond1 == true || cond2 == true)){
    return myrule.concl;
  }

  //else returns error message
  else return "error";
}

// =============== THE MAIN =================

int main() {
  cout << "Reading the KB..." << endl;
  readin();
  cout << "DEDUCED:" << endl;
  if (num == 1) cout << deduceOne() << endl;
  else cout << deduceTwo() << endl;
  
  return 0;
}
