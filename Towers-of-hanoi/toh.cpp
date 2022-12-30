#include<string>
#include<vector>
#include<iostream>
#include<stdlib.h>  /* to use exit */
using namespace std;

// FALL22 
// Read the main() first and write down all the functions it calls!
//--------------------------------------------------
// YOUR NAME: ** Brian Ramirez
// Tested on Empress using g++  
//--------------------------------------------------

// State description class.
// Could have used struct.
// disks here is a string of L, M, or R to represent left peg, middle peg, right peg
// e.g. LLL for the initial state
// first character of disks will represent the small disk, then medium disk 
// and back of string is large disk
class state
{
public:
  string disks;  // made up of L M and R going from LLL to RRR
  string camefrom; // the parent on the solution path
  int g;   // cost so far
  int h;   // estimated cost to the goal
  int f;   // g+h
};

// Major data structures:
vector<state> frontier;  // Frontier nodes - will pick one of these to expand
vector<state> beenThere; // already expanded nodes

// ========== Utility Functions ===================

// utility to add x to the beenthere
void addtoBeenThere(state x)
{  beenThere.push_back(x); }

// utility function to add x to the frontier
void addtoFrontier(state x)
{  frontier.push_back(x); }

//uitility function to check beenThere
bool inBeenThere(state x){
  //loop through beenThere vector
  for(int i=0; i < beenThere.size(); i++){
    //check if disks match anything in beenThere
    if(x.disks == beenThere[i].disks){
      //if it matches return true
      return true;
    }
  }  
  //else it is not in beenThere and return false 
  return false;
}

/******************************************************************
 * @brief This function is a utility function to check whether a
 *        generated state is safe/legal.
 * 
 * @param x 
 * @return true 
 * @return false 
 *******************************************************************/
//utility function to check if unsafe
bool isUnsafe(state x){
  // check if medium disk is on top of small disk on middle peg
  // when x came from MLL
  if(x.disks == "MML" && x.camefrom == "MLL"){
    return true;
  }
  // check if medium disk is on top of small disk on middle peg
  // when x came from MRL
  if(x.disks == "MML" && x.camefrom == "MRL"){
    return true;
  }
  // check if medium disk is on top of small disk on middle peg
  // when x came from MRR
  if(x.disks == "MMR" && x.camefrom == "MRR"){
    return true;
  }
  // check if medium disk is on top of small disk on middle peg
  // when x came from MLR
  if(x.disks == "MMR" && x.camefrom == "MLR"){
    return true;
  }
  // check if medium disk is on top of small disk on middle peg
  // when small disk on large disk and x came from MLM
  if(x.disks == "MMM" && x.camefrom == "MLM"){
    return true;
  }
  // check if medium disk is on top of small disk on middle peg
  // when small disk on large disk and x came from MRM
  if(x.disks == "MMM" && x.camefrom == "MRM"){
    return true;
  }
  // check if large disk is on top of small disk on middle peg
  // when x came from MLR
  if(x.disks == "MLM" && x.camefrom == "MLR"){
    return true;
  }
  // check if large disk is on top of small disk on middle peg
  // when x came from MRL
  if(x.disks == "MRM" && x.camefrom == "MRL"){
    return true;
  }
  // check if large disk is on top of small disk on middle peg
  // when small disk on medium disk and x came from MML
  if(x.disks == "MMM" && x.camefrom == "MML"){
    return true;
  }
  // check if large disk is on top of small disk on middle peg
  // when small disk on medium disk and x came from MMR
  if(x.disks == "MMM" && x.camefrom == "MMR"){
    return true;
  }
  // check if large disk is on top of medium disk on middle peg
  // when x came from RML
  if(x.disks == "RMM" && x.camefrom == "RML"){
    return true;
  }
  // check if large disk is on top of medium disk on middle peg
  // when x came from LMR
  if(x.disks == "LMM" && x.camefrom == "LMR"){
    return true;
  }

  // check if medium disk is on top of small disk on right peg
  // when x came from RLL
  if(x.disks == "RRL" && x.camefrom == "RLL"){
    return true;
  }
  // check if medium disk is on top of small disk on right peg
  // when x came from RMM
  if(x.disks == "RRM" && x.camefrom == "RMM"){
    return true;
  }
  // check if medium disk is on top of small disk on right peg
  // when x came from RLM
  if(x.disks == "RRM" && x.camefrom == "RLM"){
    return true;
  }
  // check if medium disk is on top of small disk on right peg
  // when x came from RML
  if(x.disks == "RRL" && x.camefrom == "RML"){
    return true;
  }
  // check if medium disk is on top of small disk on right peg
  // when small disk on large disk and x came from RMR
  if(x.disks == "RRR" && x.camefrom == "RMR"){
    return true;
  }
  // check if medium disk is on top of small disk on right peg
  // when small disk on large disk and x came from RMR
  if(x.disks == "RRR" && x.camefrom == "RLR"){
    return true;
  }
  // check if large disk is on top of small disk on right peg
  // when x came from RML
  if(x.disks == "RMR" && x.camefrom == "RML"){
    return true;
  }
  // check if large disk is on top of small disk on right peg
  // when x came from RLM
  if(x.disks == "RLR" && x.camefrom == "RLM"){
    return true;
  }
  // check if large disk is on top of small disk on right peg
  // when small disk on medium disk and x came from RRL
  if(x.disks == "RRR" && x.camefrom == "RRL"){
    return true;
  }
  // check if large disk is on top of small disk on right peg
  // when small disk on medium disk and x came from RRM
  if(x.disks == "RRR" && x.camefrom == "RRM"){
    return true;
  }
  // check if large disk is on top of medium disk on right peg
  // when x came from LRM
  if(x.disks == "LRR" && x.camefrom == "LRM"){
    return true;
  }
  // check if large disk is on top of medium disk on right peg
  // when x came from MRL
  if(x.disks == "LRR" && x.camefrom == "MRL"){
    return true;
  }

  // check if medium disk is on top of small disk on left peg
  // when x came from LMM
  if(x.disks == "LLM" && x.camefrom == "LMM"){
    return true;
  }
  // check if medium disk is on top of small disk on left peg
  // when x came from LRR
  if(x.disks == "LLR" && x.camefrom == "LRR"){
    return true;
  }
  // check if medium disk is on top of small disk on left peg
  // when x came from LMR
  if(x.disks == "LLR" && x.camefrom == "LMR"){
    return true;
  }
  // check if medium disk is on top of small disk on left peg
  // when x came from LRM
  if(x.disks == "LLM" && x.camefrom == "LRM"){
    return true;
  }
  // check if medium disk is on top of small disk on left peg
  // when small disk on large disk and x came from LLM
  if(x.disks == "LLL" && x.camefrom == "LLM"){
    return true;
  }
  // check if medium disk is on top of small disk on left peg
  // when small disk on large disk and x came from LLR
  if(x.disks == "LLL" && x.camefrom == "LLR"){
    return true;
  }
  // check if large disk is on top of small disk on left peg
  // when x came from LRM
  if(x.disks == "LRL" && x.camefrom == "LRM"){
    return true;
  }
  // check if large disk is on top of small disk on left peg
  // when x came from LMR
  if(x.disks == "LML" && x.camefrom == "LMR"){
    return true;
  }
  // check if large disk is on top of small disk on left peg
  // when small disk on medium disk and x came from LML
  if(x.disks == "LLL" && x.camefrom == "LML"){
    return true;
  }
  // check if large disk is on top of small disk on left peg
  // when small disk on medium disk and x came from LRL
  if(x.disks == "LLL" && x.camefrom == "LRL"){
    return true;
  }
  // check if large disk is on top of medium disk on left peg
  // when x came from MLR
  if(x.disks == "MLL" && x.camefrom == "MLR"){
    return true;
  }
  // check if large disk is on top of medium disk on left peg
  // when x came from RLM
  if(x.disks == "RLL" && x.camefrom == "RLM"){
    return true;
  }

  //else state is safe and return false
  return false;
} //end of isUnsafe() function


/*****************************************************************
 * @brief This function removes state x when it has been expanded
 * 
 * @param x 
 *****************************************************************/
// to remove state x from the frontier when it has been expanded
void removefromFrontier(state x)
{ 
  vector<state> newfrontier;
  for (int k = 0; k < frontier.size(); k++)
    if (frontier[k].disks != x.disks)
      newfrontier.push_back(frontier[k]);

  frontier = newfrontier;
}

//============= At the Very End ====================

/**********************************************************************************
 * @brief This function traces the path from end goal to initial goal recursively
 * 
 * @param goal 
 **********************************************************************************/

// Trace and display the solution path from goal to initial.
// Note that camefrom of each state should be used. (called from generate)
void tracePath(state goal)
{
  // *** add code here to display the path from goal to initial
  // using cameFrom's  -- recursion is useful to not to depend on a particular order of state in Been There!
  //if camefrom is empty (LLL) then just return
  if(goal.camefrom == ""){
    return;
  }
  //else recursively call through camefrom
  else{
    cout << "came from\n" << goal.camefrom << endl;
    //find matching state where disks == camefrom
    for(int i=0; i<beenThere.size(); i++){
      if(goal.camefrom == beenThere[i].disks){
        goal = beenThere[i];
      }
    }
    //recursive call
    tracePath(goal);
  }
}// end of tracePath  

//======= For Generating a new state to add to Frontier  =============

// Check to see if next's disks is already in frontier and return true or false.
//   If it is already in frontier, 
//   and if the next's f is better, 
//   update the frontier node to next.  (called from generate)
bool inFrontier(state next)
{
  // **** add code here, possibly updating
  // the same frontier node with next if f is better.
  for (int i=0; i<frontier.size(); i++){
    if(next.disks == frontier[i].disks){
      // Please cout a message in that case.
      cout << "already in the frontier!\n";
      cout << "f is not better\n";

      //if f is better
      if(next.f < frontier[i].f){
        frontier[i].f = next.f;
        cout << "updated the f value of " << next.disks << endl;
      }

      // Return true or false.
        return true;
    }
  }
  //else state is not in frontier
  //and return false
  return false;
}// end of inFrontier


/**********************************************************************
 * @brief This function tries to add next state to froniter but will
 *        stop if it reaches RRR
 * 
 * @param next 
 ***********************************************************************/
// Try to add next to frontier but stop if RRR (called from generateAll)
void generate(state next)
{ 
  cout << "Trying to add: " << next.disks;

  if (next.disks  == "RRR")  // the goal is reached
  { 
    cout << "\n>>Reached: " << next.disks << endl;
    tracePath(next);  // display the solution path
    exit(0); 
  }//done

  // *** if been there before, do not add to frontier.
  // You may write a utility function check been-there and call it
  if(inBeenThere(next)){
    cout << " already been there!\n";
    return;
  }
  // *** if unsafe, do not add to frontier.
  // You may write a function to check unsafe and call it
  if(isUnsafe(next)){
    cout << " unsafe!\n";
  }
  else{
    cout << " safe!\n";
    // else compute h and then f for next
    int h = 0;// start point for h; g is already set
    // ** update h and then f
    //count how many disks still not on right side
    for(int i=0; i<3; i++){
      if(next.disks[i] != 'R'){
        h += 1;
      }
    }
    if(next.disks[0] == 'R' && next.disks[1] == 'R'){
      if(next.disks[2] != 'R'){
        h += 1;
      }
    }
    if(next.disks[0] == 'R' && next.disks[2] == 'R'){
      if(next.disks[1] != 'R'){
        h += 1;
      }
    }
    //update h
    next.h = h;
    next.f = next.h + next.g;

    if (!inFrontier(next)){
      cout << "not in frontier yet!\n";
      addtoFrontier(next); // add next to Frontier
    }
    //reset h
    h = 0;
  }
}// end of generate   



// Generate all new states from current (called from main)
void generateAll(state current)
{
  state next;  // a generated one

  // each next will be modified from current for ease
  current.g = current.g + 1; // to update g to be used for next
  // storing the parent so that we can produce the solution path
  current.camefrom = current.disks; 

  // check all possibilities as follows..

  // MOVING SMALL DISK
  // move small disk to middle peg and right peg
  next = current;  // starting point of next is current
  if (current.disks[0] == 'L'){ 
    next.disks[0]='M'; 
    generate(next);
    next.disks[0]='R';
    generate(next);
  }
  // move small disk to left peg and right peg
  next = current;  // starting point of next is current
  if (current.disks[0] == 'M'){ 
    next.disks[0]='L'; 
    generate(next);
    next.disks[0]='R';
    generate(next);
  }
  // move small disk to left peg and middle peg
  next = current;  // starting point of next is current
  if (current.disks[0] == 'R'){ 
    next.disks[0]='M'; 
    generate(next);
    next.disks[0]='L';
    generate(next);
  }

  // MOVING MEDIUM DISK
  // move medium disk to middle peg and right peg
  next = current;  // starting point of next is current
  if (current.disks[1] == 'L' && current.disks[0] != 'L'){ 
    next.disks[1]='M'; 
    generate(next);
    next.disks[1]='R';
    generate(next);
  }
  // move medium disk to left peg and right peg
  next = current;  // starting point of next is current
  if (current.disks[1] == 'M' && current.disks[0] != 'M'){ 
    next.disks[1]='L'; 
    generate(next);
    next.disks[1]='R';
    generate(next);
  }
  // move medium disk to left peg and middle peg
  next = current;  // starting point of next is current
  if (current.disks[1] == 'R' && current.disks[0] != 'R'){ 
    next.disks[1]='M'; 
    generate(next);
    next.disks[1]='L';
    generate(next);
  }

  // MOVING LARGE DISK
  // move large disk to middle peg and right peg
  next = current;  // starting point of next is current
  if (current.disks[2] == 'L'){ 
    if(current.disks[0] != 'L' && current.disks[1] != 'L'){
      next.disks[2]='M'; 
      generate(next);
      next.disks[2]='R';
      generate(next);
    }
  }
  // move large disk to left peg and right peg
  next = current;  // starting point of next is current
  if (current.disks[2] == 'M'){
    if(current.disks[0] != 'M' && current.disks[1] != 'M'){
      next.disks[2]='L'; 
      generate(next);
      next.disks[2]='R';
      generate(next);
    }
  }
  // move large disk to left peg and middle peg
  next = current;  // starting point of next is current
  if (current.disks[2] == 'R'){
    if(current.disks[0] != 'R' && current.disks[1] != 'R'){
      next.disks[2]='M'; 
      generate(next);
      next.disks[2]='L';
      generate(next);
    }
  }
 
}// end of generateAll

// Find the best f state out of the frontier and return it (called from main)
state bestofFrontier()
{ 
  // **** add code here
  state min;  //holds state with the current minimum
  //set min as first state in frontier vector
  min = frontier[0];
  //loop until we find min in frontier
  for(int i=1; i< frontier.size(); i++){
    if(frontier[i].f <= min.f){
      if(frontier[i].h < min.h){
        min = frontier[i];
      }
    }
  }
  //display the best state with lowest f
  cout << "Best is: " << min.disks << endl;
  //return min state
  return min;
}//end of bestofFrontier

// Display the states in the frontier  (called from main)
void displayFrontier()
{
  for (int k = 0; k < frontier.size(); k++)
    { cout << frontier[k].disks << " ";  
      cout << "g = " <<frontier[k].g << " ";
      cout << "h = " <<frontier[k].h << " ";
      cout << "f = " <<frontier[k].f << endl; 
    }
}// end of displayFrontier

//================= Main ===========================

int main() 
{

  state  current = {"LLL","", 0, 3, 3};  // initial
  addtoFrontier(current);

  cout << "First char represents small disk, second char represents medium disk\nand third char represents large disk. ";
  cout << "Initial state is all disks on left e.g. 'LLL'\n";
  char ans;
  while ( ans != 'n')
    { 
      removefromFrontier(current);      
      addtoBeenThere(current);

      cout << ">>>Expand: " << current.disks << endl; 
      generateAll(current);  // new states are added to frontier

      cout << "Frontier is:" << endl;
      displayFrontier();

      current = bestofFrontier(); // pick the best state out of frontier
      cin >> ans;
      
      //clear frontier because previous moves will become unreachable
      //due to top disks being the only legal moves
      frontier.clear();
    }// end while

}//end of main

