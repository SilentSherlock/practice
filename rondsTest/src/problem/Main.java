package problem;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.util.*;
/*
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        LinkedList<Integer> zeroIndexs = new LinkedList<>();
        for (int i = 0;i < n;i++) {
            int cur = sc.nextInt();
            if (cur == 0) {
                zeroIndexs.add(i);
            }
        }
        if (zeroIndexs.size() <= k || zeroIndexs.size() == 0) System.out.println(n);
        else if (zeroIndexs.size() == n) System.out.println(k);
        else {
            int result = new Main().getLongestSubstring(zeroIndexs, k, n);
            System.out.println(result);
        }
    }

    public int getLongestSubstring(LinkedList<Integer> zeroIndexs, int k, int n) {
        int length = zeroIndexs.size();
        int result = Integer.MIN_VALUE;
        for (int i = 0;i <= length-k;i++) {
            int current = 0;
            if (i == 0) {
                current = zeroIndexs.get(k);
            }else if (i == length-k) {
                current = n - (zeroIndexs.get(i-1)+1);
            }else {
                current = zeroIndexs.get(i+k)- (zeroIndexs.get(i-1)+1);
            }
            result = Math.max(result, current);
        }

        return result;
    }
}*/

/*
public class Main {

    Result result = new Result(0, 0);
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int row = sc.nextInt();
        int col = sc.nextInt();
        int[][] matrix = new int[row][col];
        for (int i = 0;i < row;i++) {
            for (int j = 0;j < col;j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        */
/*Main main = new Main();*//*

        Result result = new Main().getInitialMatrix(matrix, 0, row-1);
        for (int i = 0;i <= result.endRow;i++) {
            for (int j = 0;j < col-1;j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print(matrix[i][col-1] + "\n");
        }
    }

    public Result getInitialMatrix(int[][] matrix, int beginRow, int endRow) {
        int rows = endRow - beginRow + 1;
        if (rows % 2 == 1) {
            return new Result(0, endRow);
        }
        */
/*int gap = rows / 2;*//*

        int begin = beginRow;
        int end = endRow;
        while (begin < end) {
            for (int j = 0;j < matrix[0].length;j++) {
                if (matrix[begin][j] != matrix[end][j]) {
                    result = new Result(0, endRow);
                    return result;
                }
            }
            begin++;
            end--;
        }
        return getInitialMatrix(matrix, beginRow, endRow/2);
    }

}
class Result {
    int beginRow;
    int endRow;
    Result(int begin, int end) {
        beginRow = begin;
        endRow = end;
    }
}*/

/*public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0;i < n;i++) {
            nums[i] = scanner.nextInt();
        }
        int result = new Main().getPurchaseCount(nums, m, k);
        System.out.println(result);
    }

    *//*
    * nums:美丽值数组
    * m:窗口大小
    * k:美丽值最低标准
    * *//*
    public int getPurchaseCount(int[] nums, int m, int k) {
        int result = 0;
        int length = nums.length;
        if (length < m) return 0;
        if (length == m) return 1;
        for (int i = 0;i <= length-m;i++) {
            int j = i;
            for (;j < i + m;j++) {
                if (nums[j] < k) break;
            }
            if (j == i+m) result++;
        }
        return result;
    }
}*/

/*
public class Main{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int d = scanner.nextInt();
        int result = new Main().getNum(n, k, d);
        System.out.println(result);
    }


    //和为n的某些数，都小于等于k且大于等于1，max(n)大于等于d，数都为正整数
    public int getNum(int n, int k, int d) {
        if (d == n || n == 1 || k == 1) return 1;
        int min = n/k;//最少有n/k个数，都为k
        int max = n;//最多有n个数，都为1
        int result = 0;
        for (int i = min;i <= max;i++) {
            for (int j = 0;j <= i;j++) {
                return n;
            }
        }
        return n;
    }
}*/

/*
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int gap = scanner.nextInt();
        scanner.nextLine();
        String str = scanner.nextLine();
        String result = new Main().decode(str, gap);
        System.out.println(result);
    }

    public String decode(String str, int gap) {
        if (str == null || str.length() == 0) return str;
        if (gap == 0 || gap == 1) return str;

        int length = str.length();
        if (length <= gap) return new StringBuilder(str).reverse().toString();
        char[] chars = str.toCharArray();
        int i = 0;
        for (;i + gap <= length;i = i+gap) {
            int begin = i;
            int end = i + gap - 1;
            while (begin < end) {
                char tmp = chars[begin];
                chars[begin] = chars[end];
                chars[end] = tmp;
                begin++;
                end--;
            }
        }
        if (i < length) {
            int begin = i;
            int end = length-1;
            while (begin < end) {
                char tmp = chars[begin];
                chars[begin] = chars[end];
                chars[end] = tmp;
                begin++;
                end--;
            }
        }
        return String.valueOf(chars);
    }
}*/

/*
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dataCount = scanner.nextInt();
        for (int i = 0;i < dataCount;i++) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            int k = scanner.nextInt();
            UnionFind unionFind = new UnionFind(n);
            for (int j = 0;j < m;j++) {
                int p = scanner.nextInt();
                int q = scanner.nextInt();
                int cost = scanner.nextInt();
                if (cost <= k) {
                    unionFind.union(p, q);
                }
            }
            if (unionFind.count() == 1) {
                System.out.println("Yes");
            }else {
                System.out.println("No");
            }
        }
    }
}
class UnionFind {

    private int[] record;
    private int count;//岛屿数目

    public UnionFind(int n) {
        record = new int[n+1];
        for (int i = 0;i <= n;i++) {
            record[i] = i;
        }
        count = n;
    }

    public int find(int p) {
        if (p != record[p]) record[p] = find(record[p]);
        return record[p];
    }

    //造桥
    public void union(int p, int q) {
        int pid = find(p);
        int qid = find(q);
        if (pid == qid) return;
        record[pid] = qid;
        count--;//联通的岛屿算成一个
    }

    public int count() {
        return count;
    }
}*/
/*
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        if (str != null && str.length() != 0) {
            int num = getNum(str);
            int[] nums = new int[num];
            for (int i = 0;i < num;i++) {
                if (i == 0 || i == 1) nums[i] = 1;
                else nums[i] = nums[i-1] + nums[i-2];
            }
            if (str.contains("一个")) System.out.println(Arrays.toString(nums));
            else if (str.contains("第"))System.out.println(nums[num-1]);
        }
    }

    private static int getNum(String str) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = str.length();i < len;i++) {
            char cur = str.charAt(i);
            if (cur >= '0' && cur <= '9') {
                builder.append(cur);
            }
        }
        return Integer.parseInt(builder.toString());
    }
}*/
/*
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        String str = scanner.nextLine();
        String str2 = scanner.nextLine();
        String[] numStr = str.split(",");
        for (String tmp : numStr) {
            list.add(getNum(tmp));
        }
        int target = getNum(str2);

        Main test = new Main();
        List<int[]> result = test.sumOfTwo(list.toArray(new Integer[]{}), target);
        for (int[] tmp : result) {
            System.out.println(tmp[0] + "," + tmp[1]);
        }
    }

    public List<int[]> sumOfTwo(Integer[] nums, int target) {
        //System.out.println("here");
        List<int[]> result = new LinkedList<>();
        int length = nums.length;
        if (length <= 1) return result;
        Arrays.sort(nums);
        if (target < nums[0]) return result;

        int left = 0, right = length-1;
        while (left < right) {
            int cur = nums[left] + nums[right];
            if (cur == target) {
                result.add(new int[]{nums[left], nums[right]});
                left++;
                right--;
            }else if (cur < target) left++;
            else right--;
        }
        return result;
    }

    private static int getNum(String str) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = str.length();i < len;i++) {
            char cur = str.charAt(i);
            if (cur >= '0' && cur <= '9') {
                builder.append(cur);
            }
        }
        return Integer.parseInt(builder.toString());
    }
}*/
/*
public class Main {
    public static void main(String[] args) {

    }

    public int calculate(int[] birth, int death, int life) {
        int[] sheepGroup = new int[death+1];//不同年龄段羊的数目
        sheepGroup[1] = 1;
        for (int i = 1;i <= life;i++) {
            for (int j = death;j >= 1;j--) {
                if (j == death) sheepGroup[j] = 0;
                else if (sheepGroup[j] == 0) continue;
                else {

                }
            }
        }
    }
}*/
/*public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] irons = new int[n];
        for (int i = 0;i < n;i++) {
            irons[i] = scanner.nextInt();
        }
        Arrays.sort(irons);
        for (int num : irons) {
            System.out.print(num + " ");
        }
    }

    //返回每列铁块数
    public int[] iron(int[][] irons) {
        int rows = irons.length;
        if(rows == 0) return new int[]{};
        int cols = irons[0].length;
        if (cols == 0) return new int[]{};
        for (int[] iron : irons) {
            Arrays.sort(iron);
        }
        int[] result = new int[cols];
        int i = 0, j = cols-1;
        while (i < rows && j >= 0) {
            if (irons[i][j] == 0) {
                i++;
            }else {
                result[j] = rows-i;
                j--;
            }
        }
        return result;
    }
}*/
/*
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        Main main = new Main();
        System.out.println(main.getScore(str));
    }

    public int getScore(String str) {
        boolean perfectFlag = false, gaming = true, lastP = false;
        int pCount = 0, mCount = 0;
        int length = str.length();
        int score = 0;
        for (int i = 0;i < length && gaming;i++) {
            char cur = str.charAt(i);
            switch (cur) {
                case 'P':
                    if (perfectFlag) {
                        score += 250;
                    } else {
                        score += 200;
                        if (!lastP) {
                            pCount = 1;
                            lastP = true;
                        }else pCount++;
                        if (pCount >= 3) {
                            perfectFlag = true;
                            pCount = 0;
                        }
                    }
                    break;
                case 'G':
                    score += 100;
                    lastP = false;
                    if (perfectFlag) perfectFlag = false;
                    break;
                case 'M':
                    if (perfectFlag) perfectFlag = false;
                    lastP = false;
                    mCount++;
                    if (mCount == 3) gaming = false;
                    break;
                default:
                    break;
            }
        }
        return score;
    }
}*/
/*
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String n = scanner.next();
        int m = scanner.nextInt();
        Main main = new Main();
        System.out.println(main.calculate(n, m));
    }

    private int result = 0;
    public int calculate(String n, int m) {
        int length = n.length();
        if (length < 10 && Integer.parseInt(n) < m) return result;
        dfs(n.toCharArray(), 0, new HashSet<>(), m);
        return result;
    }

    public void dfs(char[] chars, int index, HashSet<String> set, int m) {
        if (index == chars.length-1) {
            String str = String.valueOf(chars);
            if (!str.startsWith("0") && !set.contains(str)) {
                long value = Long.parseLong(str);
                set.add(str);
                if (value % (long) m == 0) result++;
            }
        }else {
            for (int i = index, len = chars.length;i < len;i++) {
                char tmp = chars[i];
                if (i != index && tmp == chars[index]) continue;
                chars[i] = chars[index];
                chars[index] = tmp;
                dfs(chars, index+1, set, m);
                chars[index] = chars[i];
                chars[i] = tmp;
            }
        }
    }
}*/
//public class Main {
//    public static void main(String[] args) {
//        /*Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        Book[] books = new Book[n];
//        for (int i = 0;i < n;i++) {
//            String str = sc.nextLine();
//            String[] strs = str.split(".");
//            books[i].id = Long.valueOf(strs[0]);
//            books[i].category = Integer.parseInt(strs[1]);
//            books[i].word = Integer.parseInt(strs[2]);
//            books[i].date = new Date(strs[3]);
//        }
//        Arrays.sort(books);
//        for (Book book : books) {
//            System.out.println(book.id);
//        }*/
//        System.out.println("放到！".length());
//    }
//
//    class Book implements Comparable<Book> {
//
//        public long id;
//        public int category;
//        public int word;
//        public Date date;
//        @Override
//        public int compareTo(Book o) {
//            if (this.category > o.category) return 1;
//            else if (this.category < o.category) return -1;
//            else {
//                if (this.date.toString().compareTo(o.date.toString()) > 0) return 1;
//                else if (this.date.toString().compareTo(o.date.toString()) < 0) return -1;
//                else {
//                    if (this.word > o.word) return 1;
//                    else if (this.word < o.word) return -1;
//                    else {
//                        if (this.id > o.id) return 1;
//                        else if (this.id < o.id) return -1;
//                        else return 0;
//                    }
//                }
//            }
//        }
//    }
//}
//public class Main {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        int[][] matrix = new int[n][n];
//        for (int i = 0;i < n;i++) {
//            for (int j = 0;j < n;j++) {
//                matrix[i][j] = sc.nextInt();
//            }
//        }
//        Main main = new Main();
//        main.rotate(matrix);
//        for (int i = 0;i < n;i++) {
//            for (int j = 0;j < n;j++)
//                System.out.println(matrix[i][j] + " ");
//            System.out.println();
//        }
//    }
//
//    public void rotate(int[][] matrix) {
//        for (int i = 0, len = matrix.length;i < len/2;i++) {
//            for (int j = 0;j < len;j++) {
//                int tmp = matrix[i][j];
//                matrix[i][j] = matrix[len-i-1][j];
//                matrix[len-i-1][j] = tmp;
//            }
//        }
//
//        for (int i = 0, len = matrix.length;i < len;i++) {
//            for (int j = 0;j < len/2;j++) {
//                int tmp = matrix[i][j];
//                matrix[i][j] = matrix[i][len-j-1];
//                matrix[i][len-j-1] = tmp;
//            }
//        }
//    }
//}
/*public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        int[] works = new int[n];
        for (int i = 0;i < n;i++) {
            works[i] = sc.nextInt();
        }
        Main main = new Main();
        int result = main.calculate(m, n, works);
        System.out.println(result);
    }

    public int calculate(int m, int n, int[] works) {
        Arrays.sort(works);
        int[] manage = new int[m];
        for (int i = n-1;i >= 0;i--) {
            manage[0] += works[i];
            Arrays.sort(manage);
        }
        return manage[m-1];
    }
}*/
public class Main {

    private String[] numbers;
    private String[] utils;
    private String full;
    private String zeroFull;

    public Main() {
        numbers = new String[]{
                "零", "壹", "贰", "叁",
                "肆", "伍", "陆",
                "柒", "捌", "玖"
        };
        utils = new String[] {
                "分", "角", "元", "拾",
                "佰", "仟", "万", "拾",
                "佰", "仟", "亿", "拾",
                "佰", "仟", "兆", "拾",
                "佰", "仟"
        };
        full = "整";
        zeroFull = "零元整";
    }

    public String transform(BigDecimal money) {
        StringBuilder builder = new StringBuilder();

        int sin = money.signum();
        if (sin == 0) return "人民币" + zeroFull;

        long number = money.movePointRight(2).setScale(0, 4).abs().longValue();
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;

        if (scale <= 0) {
            numIndex = 2;
            number = numIndex / 100;
            getZero = true;
        }

        if (scale > 0 && scale % 10 <= 0) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) break;

            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if (numIndex == 9 && zeroSize >= 3) {
                    builder.insert(0, utils[6]);
                }
                if (numIndex == 13 && zeroSize >= 3) {
                    builder.insert(0, utils[10]);
                }
                builder.insert(0, utils[numIndex]);
                if (numUnit > 1 || number / 10 > 0) builder.insert(0, numbers[numUnit]);
                getZero = false;
                zeroSize = 0;
            }else {
                ++zeroSize;
                if (!getZero) {
                    builder.insert(0, numbers[numUnit]);
                }
                if (numIndex == 2) {
                    builder.insert(0, utils[numIndex]);
                } else if ((numIndex - 2) % 4 == 0 && (number % 1000 > 0)) {
                    builder.insert(0, utils[numIndex]);
                }
                getZero = true;
            }
            number /= 10;
            ++numIndex;
        }
        if (scale <= 0) {
            builder.append(full);
        }

        return "人民币" + builder.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double d = scanner.nextDouble();
        Main main = new Main();
        System.out.println(main.transform(new BigDecimal(d)));
    }
}
