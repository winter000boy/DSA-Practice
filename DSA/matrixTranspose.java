package DSA;

public class matrixTranspose {

    public static void main(String[] args) {
        int [][] matrrix = {{1,2,3},{4,5,6},{7,8,9}};

        int[][] Transpose = new int[3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3; j++){
                Transpose[j][i] = matrrix[i][j];
            }
        }

        for( int[] i:Transpose){
            for (int j : i){
                System.out.print(j+" ");
            }
            System.out.println();
        }

        for( int[] i:matrrix){
            for (int j : i){
                System.out.print(j+" ");
            }
            System.out.println();
        }
    }
}
