import cli.Cli;

/**
 * Program startup class
 */
public class Main {

    /**
     * Program's entry point
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        new Cli().run();

//        System.out.println("Enter the name of each symptoms you want");
//        System.out.println("When you have entered all of the wanted symptoms, type :q to find the result");
//        Scanner sc = new Scanner(System.in);
//        String curr = "";
//        StringBuilder query = new StringBuilder();
//        while(!curr.equals(":q")) {
//            curr = sc.nextLine();
//            query.append(' ');
//            query.append(LuceneSearcherBase.getFieldForLuceneExactSearchOn(curr));
//        }
//
//        // Dummy request for pre-prod test purposes
//        DiagnosticRequest diagnosticRequest = new DiagnosticRequest();
//        diagnosticRequest.setUndesirableEffect(query.toString());
//
//        DiagnosticResponse diagnosticResponse = DiagnosticManager.generateDiagnostic(diagnosticRequest);
//
//        List<Disease> diseaseCauses = diagnosticResponse.getDiseaseCauses();
//        System.out.println("-------------------------------------");
//        System.out.println("Possible diseases".toUpperCase());
//        System.out.println("-------------------------------------");
//        for (Disease d: diseaseCauses) {
//            System.out.println(d.getName());
//        }
//        System.out.println("-------------------------------------");
//        System.out.println("Possible drug causes".toUpperCase());
//        System.out.println("-------------------------------------");
//        List<Drug> drugCauses = diagnosticResponse.getDrugCauses();
//        for (Drug d: drugCauses) {
//            System.out.println(d.getName());
//        }

    }

}
