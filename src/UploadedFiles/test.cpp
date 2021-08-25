// C++ program to output the maximum occurring character
// in a string
#include<bits/stdc++.h>
#define ASCII_SIZE 256
using namespace std;

char getMaxOccuringChar(int arr[])
{
	// Create array to keep the count of individual
	// characters and initialize the array as 0
	int count[ASCII_SIZE] = {0};

	// Construct character count array from the input
	// string.
	int len = sizeof(arr[])/sizeof(arr[0]);
	int max = 0; // Initialize max count
	int result; // Initialize result

	// Traversing through the string and maintaining
	// the count of each character
	for (int i = 0; i < len; i++) {
		count[arr[i]]++;
		if (max < count[arr[i]]) {
			max = count[arr[i]];
			result = arr[i];
		}
	}

	return result;
}

// Driver program to test the above function
int main()
{
	int arr[] = {1, 2, 3, 3, 4, 3, 6};
	cout << "Max occurring character is "
		<< getMaxOccuringChar(arr);
