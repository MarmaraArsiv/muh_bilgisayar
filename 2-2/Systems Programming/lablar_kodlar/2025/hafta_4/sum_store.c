int plus(int x, int y){
	return x+y;
}
void sumstore(int x, int y, int *dest){
	int t = plus(x, y);
	*dest = t;
}