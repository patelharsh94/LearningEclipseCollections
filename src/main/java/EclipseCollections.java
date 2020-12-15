import java.util.*;
import java.util.stream.Collectors;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.partition.list.PartitionMutableList;
import org.eclipse.collections.impl.collector.Collectors2;
import org.junit.Test;

// Just a test object
class Address {
    String homeAddress;
    String street;
    String zip;

    Address(String home, String street, String zip) {
        this.homeAddress = home;
        this.street = street;
        this.zip = zip;
    }
}

public class EclipseCollections {

    MutableList<Integer> numberList;
    long startTime, endTime;

    @Test
    public void basics() {

        // creating a list
        numberList = Lists.mutable.of(1, 2, 3, 4, 6, 10, 50, 60, 70, 80, 90, 100);
        System.out.println("Original List: " + numberList.toString());

        // adding to a list
        numberList.add(129);
        System.out.println("After adding 129: " + numberList.toString());

        // removing from list (accepts an index) therefore 3 will remove the value 4
        numberList.remove(3);
        System.out.println("After removing from index 3: " + numberList.toString());

    }

    @Test
    public void learningSelectReject() {
        // creating a list
        numberList = Lists.mutable.of(1, 2, 3, 4, 6, 10, 50, 60, 70, 80, 90, 100);

        // selecting values
        MutableList<Integer> greaterThanFifty = numberList.select(num -> num > 50);
        System.out.println("Numbers greater than 50: " + greaterThanFifty.toString());

        // rejecting values
        startTime = System.nanoTime();
        MutableList<Integer> lowerThanFifty = numberList.select(num -> num <= 50);
        endTime = System.nanoTime();
        System.out.println("Numbers lower than or equal to 50: " + lowerThanFifty.toString());
        System.out.println("Total time to run: " + (endTime - startTime));


        // Comparison with java streams
        startTime = System.nanoTime();
        MutableList<Integer> notGreaterThanFifty = numberList.stream()
                .filter(each -> each <= 50)
                .collect(Collectors2.toList());
        endTime = System.nanoTime();
        System.out.println("Numbers lower than or equal to 50 (Java streams): " + notGreaterThanFifty.toString());
        System.out.println("Total time to run: " + (endTime - startTime));
    }

    @Test
    public void partition() {
        // creating a list
        numberList = Lists.mutable.of(1, 2, 3, 4, 6, 10, 50, 60, 70, 80, 90, 100);

        startTime = System.nanoTime();
        PartitionMutableList<Integer> partitionedIntegers = numberList.partition(num -> num > 50);
        System.out.println("Greater than 50: " + partitionedIntegers.getSelected());
        System.out.println("Less than or equal to 50: " + partitionedIntegers.getRejected());
        endTime = System.nanoTime();
        System.out.println("Total run time for partition and print: " + (endTime - startTime));

        // Java 8 streams implementation
        startTime = System.nanoTime();
        Map<Boolean, List<Integer>> numJava8 = numberList
                .stream()
                .collect(Collectors.partitioningBy((num) -> num > 50));
        System.out.println("Greater than 50: " + numJava8.get(Boolean.TRUE));
        System.out.println("Less than or equal to 50: " + numJava8.get(Boolean.FALSE));
        endTime = System.nanoTime();
        System.out.println("Total run time for partition and print (java 8): " + (endTime - startTime));

    }

    @Test
    public void collect() {

        // EC implementation

        startTime = System.nanoTime();
        MutableList<Address> addressList = Lists.mutable.of(
                new Address("Street Address 1", "Street 1", "12345"),
                new Address("Street Address 2", "Street 2", "54321"),
                new Address("Street Address 3", "Street 3", "67899"));
        MutableList<String> addressCollection = addressList.collect(addr -> addr.street);
        endTime = System.nanoTime();
        System.out.println("Address collection: " + addressCollection + " Total Time: " + (endTime - startTime));

        // Java 8 stream
        startTime = System.nanoTime();
        addressCollection = addressList.stream()
                .map(person -> person.street)
                .collect(Collectors2.toList());
        endTime = System.nanoTime();
        System.out.println("Address collection: " + addressCollection + " Total Time: " + (endTime - startTime));
    }

    @Test
    public void collectIf() {
        numberList = Lists.mutable.of(1, 2, 3, 4, 6, 10, 50, 60, 70, 80, 90, 100);
        MutableList<Integer> numbersGreaterThen50 = Lists.mutable.of();
        numberList.collectIf(num -> num > 50, num -> num+10, numbersGreaterThen50);
        System.out.println("Numbers bigger than 50 + 10 : " + numbersGreaterThen50);
    }

    @Test
    public void detect() {
        // Finds the first element that satisfies a givan logical expression.
        // EC lib
        MutableList<Address> addressList = Lists.mutable.of(
                new Address("Street Address 1", "Street 1", "12345"),
                new Address("Street Address 2", "Street 2", "54321"),
                new Address("Street Address 3", "Street 3", "67899"));
        Address str2 = addressList.detect(addr -> addr.street.contains("2"));
        System.out.println("Address for street 2 : " + str2.homeAddress);
    }

    @Test
    public void forEach() {
        MutableList<Address> addressList = Lists.mutable.of(
                new Address("Street Address 1", "Street 1", "12345"),
                new Address("Street Address 2", "Street 2", "54321"),
                new Address("Street Address 3", "Street 3", "67899"));

        addressList.each(each -> System.out.println("Address: " + each.homeAddress));
    }

}
