package burp.ui;

public class ObjectArrayInsertion {
    public static void main(String[] args) {
        // 创建一个Object[1][]数组对象
        Object[][] array = new Object[1][];

        // 创建要插入的数据
        String enable = "true";
        String item = "Request header";
        String match = "Connection: .*";
        String replace = "Connection:closssssssclosssssssclosssssssclosssssss";
        String type = "Regex";
        String comment = "Comment 1";

        // 在指定位置插入数据
        int row = 0;
        int column = 0;
        array[row] = new Object[column + 1];
        array[row][column] = new Object[]{enable, item, match, replace, type, comment};

        // 打印插入后的数据
        Object[] insertedData = (Object[]) array[row][column];
        System.out.println("Enable: " + insertedData[0]);
        System.out.println("Item: " + insertedData[1]);
        System.out.println("Match: " + insertedData[2]);
        System.out.println("Replace: " + insertedData[3]);
        System.out.println("Type: " + insertedData[4]);
        System.out.println("Comment: " + insertedData[5]);
    }
}