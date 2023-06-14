import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Application {

    public static int [][] map = new int[12][18];
    public static Scanner scanner = new Scanner(System.in);
    public static Random ran = new Random();
    public static Player vgo = new Player("Влюбеният Гном Отело", "ВГО", '#',0,0);
    public static Player sbd = new Player("Синдиката на безработните джуджета", "СБД", '$',0,0);
    public static Player lfs = new Player("Лилавния фокусник Шмандалф", "ЛФШ", '%',0,0);


    public static List<Integer> addedNumbers = new ArrayList<>();

    public static int lastPositionVGO=0;
    public static int lastPositionLFS= 217;

    //констатни за цвят, които използвам при печатане на офиса и играчите с цел да се отличават
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static List<Player> players = new ArrayList<>();
    public static void main(String[] args) {

        players.add(vgo);
        players.add(sbd);
        players.add(lfs);

        printInitialMap();

        sbd.setInitialPositionRow(sbd.getPositionRow());
        sbd.setInitialPositionCol(sbd.getPositionCol());
        vgo.setInitialPositionRow(vgo.getPositionRow());
        vgo.setInitialPositionCol(vgo.getPositionCol());
        lfs.setInitialPositionRow(lfs.getPositionRow());
        lfs.setInitialPositionCol(lfs.getPositionCol());

        startGame();
    }

    public static void setNumbers(){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++){
                if((i==5 && j==8) || (i==5 && j==9) || (i==6 && j==8) || (i==6 && j==9)){
                    map[i][j]=0;
                }
                else if((i==0 && j==0) || (i==0 && j==17) || (i==11&&j==0) || (i==11 && j==17)){
                    map[i][j]=-1;
                }
                else {
                        map[i][j] = generateRandomNumber();
                }
            }
        }
    }

    public static int generateRandomNumber(){
        int num=0;
        do {
            num = ran.nextInt(216) + 1;
        }while(addedNumbers.contains(num));
        addedNumbers.add(num);
        return num;
    }


    public static void printInitialMap(){
        setNumbers();
        placePlayers();
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++){
                if(map[i][j]==0){
                    System.out.print(ANSI_GREEN);
                    System.out.printf("%3s%5s","X"," ");
                    System.out.print(ANSI_RESET);
                }
                else if(map[i][j]==-1){
                    char output = '@';
                    for(Player p : players){
                        if(p.getPositionRow()==i && p.getPositionCol()==j)
                            output = p.getSymbol();
                    }
                    System.out.print(ANSI_YELLOW);
                    System.out.printf("%3s%5s",output," ");
                    System.out.print(ANSI_RESET);
                }
                else System.out.printf("%3d%5s",map[i][j]," ");
            }
            System.out.println();
        }


    }

    public static void oneRotation(){
        System.out.println("Кой герой искате да преместите?");
        System.out.println("1. ВГО - # \n2. СБД -$\n3. ЛФШ - %");
        int answer = scanner.nextInt();
        switch (answer){
            case 1:
                moveVGO();
                break;
            case 2:
                moveSBD();
                break;
            case 3:
                moveLFS();
                break;
        }
    }

    public static void startGame(){
        System.out.println("\nЗапочване на играта, имате на разположение 15 хода!");
        for(int i=0;i<15;i++){
            oneRotation();
            System.out.printf("\nОстават ви %d хода.\n",(15-(i+1)));
        }

        if(map[sbd.getPositionRow()][sbd.getPositionCol()]==0){
            System.out.println("Играч sbd стигна до офиса, играта е завършена успешно!");
        }else if(map[vgo.getPositionRow()][vgo.getPositionCol()]==0){
            System.out.println("Играч vgo стигна до офиса, играта е завършена успешно!");
        }
        else if(map[lfs.getPositionRow()][lfs.getPositionCol()]==0){
            System.out.println("Играч lfs стигна до офиса, играта е завършена успешно!");
        }
        else System.out.println("Нито един от играчите не успя да стигне до офиса, играта приключва!");
    }

    public static void placePlayers(){
        List<Integer> alreadyFilledPositions = new ArrayList<>();
        int randomIndex;
        for(Player p : players){
            do {
                 randomIndex = ran.nextInt(4) + 1;
            }while (alreadyFilledPositions.contains(randomIndex));

           alreadyFilledPositions.add(randomIndex);
            switch (randomIndex){
                case 1:
                    p.setPositionRow(0);
                    p.setPositionCol(0);
                    break;
                case 2:
                    p.setPositionRow(0);
                    p.setPositionCol(17);
                    break;
                case 3:
                    p.setPositionRow(11);
                    p.setPositionCol(0);
                    break;
                case 4:
                    p.setPositionRow(11);
                    p.setPositionCol(17);
                    break;
            }
        }
    }

    public static String findPlayerPosition(Player player){
        String position="";
        int col=player.getInitialPositionCol();
        int row = player.getInitialPositionRow();

        if(col ==0 && row ==0)
            position = "upLeft";
        else if(col == 17 && row ==0)
            position = "upRight";
        else if(col == 0 && row ==11)
            position = "downLeft";
        else if(col == 17 && row ==11)
            position = "downRight";

        return position;
    }



    public static void moveSBD(){
        String position="";
        int chosenPosition=0;
        int col = sbd.getPositionCol();
        int row = sbd.getPositionRow();
        int newCol =0, newRow =0;
        position = findPlayerPosition(sbd);


        if(position == "upLeft"){
            newCol = col+1;
            newRow = row+1;
        }
        else if(position == "upRight"){
            newCol = col -1;
            newRow = row +1;
        }
        else if(position == "downLeft"){
            newCol = col+1;
            newRow = row-1;
        }
        else if(position == "downRight"){
            newCol = col-1;
            newRow = row -1;
        }


        if(map[newRow][col]%2==0 ){
            System.out.printf("\nИграч %s може да се придвижи до поле %d или поле %d, Изберете поле: ", sbd.getSymbol(), map[newRow][col], map[row][newCol]);
            chosenPosition = scanner.nextInt();
            if(map[newRow][col]==chosenPosition ){
                sbd.setPositionCol(col);
                sbd.setPositionRow(newRow);
                System.out.printf("\nИграч %s е на поле %d",sbd.getSymbol(),map[newRow][col]);
            }
            else if(map[row][newCol]==chosenPosition ){
                sbd.setPositionCol(newCol);
                sbd.setPositionRow(row);
                System.out.printf("\nИграч %s е на поле %d",sbd.getSymbol(),map[row][newCol]);
            }
        }else if(map[newRow][col]%2==0){
            sbd.setPositionCol(col);
            sbd.setPositionRow(newRow);
            System.out.printf("\nИграч %s е на поле %d",sbd.getSymbol(),map[newRow][col]);

        } else if(map[row][newCol]%2==0 ){
            sbd.setPositionCol(newCol);
            sbd.setPositionRow(row);
            System.out.printf("\nИграч %s е на поле %d",sbd.getSymbol(),map[row][newCol]);
        }
        else System.out.printf("\nИграч %s остава на същата позиция.",sbd.getSymbol());

    }

    public static void moveVGO(){
        List<Integer> neighbours = new ArrayList<>();
        String position = "";
        int chosenPosition=0;
        int col = vgo.getPositionCol();
        int row = vgo.getPositionRow();



            position = findPlayerPosition(vgo);

            if (position == "upLeft") {
                System.out.printf("\nИграч %s може да се придвижи към поле %d или поле %d. Изберете поле: ", vgo.getSymbol(), map[row + 1][col], map[row][col + 1]);
                chosenPosition = scanner.nextInt();
                if(chosenPosition==map[row+1][col]){
                    int newRow = row+1;
                    vgo.setPositionRow(newRow);
                    neighbours.add(map[newRow][col]);
                    neighbours.add(map[newRow+1][col]);
                    neighbours.add(map[newRow-1][col+1]);
                    neighbours.add(map[newRow][col+1]);
                    neighbours.add(map[newRow+1][col+1]);
                }
                else{
                    int newCol = col+1;
                    vgo.setPositionCol(newCol);
                    neighbours.add(map[row][newCol]);
                    neighbours.add(map[row+1][newCol-1]);
                    neighbours.add(map[row+1][newCol]);
                    neighbours.add(map[row][newCol+1]);
                    neighbours.add(map[row+1][newCol+1]);
                }
            } else if (position == "upRight") {
                System.out.printf("\nИграч %s може да се придвижи към поле %d или поле %d. Изберете поле: ", vgo.getSymbol(), map[row + 1][col], map[row][col - 1]);
                chosenPosition = scanner.nextInt();

                if(chosenPosition==map[row+1][col]){
                    int newRow = row+1;
                    vgo.setPositionRow(newRow);
                    neighbours.add(map[newRow][col]);
                    neighbours.add(map[newRow-1][col-1]);
                    neighbours.add(map[newRow][col-1]);
                    neighbours.add(map[newRow+1][col-1]);
                    neighbours.add(map[newRow+1][col]);
                }
                else{
                    int newCol = col-1;
                    vgo.setPositionCol(newCol);
                    neighbours.add(map[row][newCol]);
                    neighbours.add(map[row+1][newCol-1]);
                    neighbours.add(map[row+1][newCol]);
                    neighbours.add(map[row][newCol-1]);
                    neighbours.add(map[row+1][newCol+1]);
                }

            } else if (position == "downLeft") {
                System.out.printf("\nИграч %s може да се придвижи към поле %d или поле %d. Изберете поле: ", vgo.getSymbol(), map[row - 1][col], map[row][col + 1]);
                chosenPosition = scanner.nextInt();

                if(chosenPosition==map[row-1][col]){
                    int newRow = row-1;
                    vgo.setPositionRow(newRow);
                    neighbours.add(map[newRow][col]);
                    neighbours.add(map[newRow-1][col]);
                    neighbours.add(map[newRow-1][col+1]);
                    neighbours.add(map[newRow][col+1]);
                    neighbours.add(map[newRow+1][col+1]);
                }
                else{
                    int newCol = col+1;
                    vgo.setPositionCol(newCol);
                    neighbours.add(map[row][newCol]);
                    neighbours.add(map[row][newCol+1]);
                    neighbours.add(map[row-1][newCol-1]);
                    neighbours.add(map[row-1][newCol]);
                    neighbours.add(map[row-1][newCol+1]);
                }
            } else if (position == "downRight") {
                System.out.printf("\nИграч %s може да се придвижи към поле %d или поле %d. Изберете поле: ", vgo.getSymbol(), map[row - 1][col], map[row][col - 1]);
                chosenPosition = scanner.nextInt();

                if (chosenPosition == map[row - 1][col]) {
                    int newRow = row - 1;
                    vgo.setPositionRow(newRow);
                    neighbours.add(map[newRow][col]);
                    neighbours.add(map[newRow - 1][col]);
                    neighbours.add(map[newRow - 1][col - 1]);
                    neighbours.add(map[newRow][col - 1]);
                    neighbours.add(map[newRow + 1][col - 1]);
                } else {
                    int newCol = col - 1;
                    vgo.setPositionCol(newCol);
                    neighbours.add(map[row][newCol]);
                    neighbours.add(map[row][newCol - 1]);
                    neighbours.add(map[row - 1][newCol - 1]);
                    neighbours.add(map[row - 1][newCol]);
                    neighbours.add(map[row - 1][newCol + 1]);
                }
        }
        int sum=0;
        for(int n : neighbours){
            sum +=n;
        }
        double average = sum/neighbours.stream().count();
        if(map[vgo.getPositionRow()][vgo.getPositionCol()]>=average){
            System.out.printf("\nИграч # се придвижва на поле %d",map[vgo.getPositionRow()][vgo.getPositionCol()]);
        }
        else {
            System.out.printf("\nИграч # не може да се премести на поле %d", map[vgo.getPositionRow()][vgo.getPositionCol()]);
            vgo.setPositionRow(row);
            vgo.setPositionCol(col);
        }
    }

    public static void moveLFS(){
        String position="";
        int chosenPosition=0;
        int col = lfs.getPositionCol();
        int row = lfs.getPositionRow();
        int newCol =0, newRow =0;
        position = findPlayerPosition(lfs);


        if(position == "upLeft"){
            newCol = col+1;
            newRow = row+1;
        }
        else if(position == "upRight"){
            newCol = col -1;
            newRow = row +1;
        }
        else if(position == "downLeft"){
            newCol = col+1;
            newRow = row-1;
        }
        else if(position == "downRight"){
            newCol = col-1;
            newRow = row -1;
        }

        System.out.printf("\nИграч %s може да се придвижи до поле %d или поле %d, Изберете поле: ", lfs.getSymbol(), map[newRow][col], map[row][newCol]);
        chosenPosition = scanner.nextInt();
        if(chosenPosition==map[newRow][col]){
            if(chosenPosition<=lastPositionLFS){
                System.out.printf("\nИграч %s е на поле %d",lfs.getSymbol(),map[newRow][col]);
                lfs.setPositionRow(newRow);
            }
            else System.out.printf("\nИграч %s остава на същата позиция, числото не е по-малко от предишното", lfs.getSymbol());
        }else{
            if(chosenPosition<=lastPositionLFS)
            {
                System.out.printf("\nИграч %s е на поле %d",lfs.getSymbol(),map[row][newCol]);
                lfs.setPositionCol(newCol);
            }
            else System.out.printf("\nИграч %s остава на същата позиция, числото не е по-малко от предишното", lfs.getSymbol());
        }

        lastPositionLFS = map[lfs.getPositionRow()][lfs.getInitialPositionCol()];
    }

}
