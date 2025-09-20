cd ..
javac -d class src/Heap.java src/Main.java src/HeapSort.java
javadoc src/HeapSort.java -d doc
jar --create --file heapsort.jar --main-class Main -C class .
java -cp class Main
java -jar heapsort.jar
