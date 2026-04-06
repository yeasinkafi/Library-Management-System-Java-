package library.management.system;

public class DeleteAllData implements IOOperation {

    @Override
    public String label() {
        return "Delete All Data";
    }

    @Override
    public void oper(AppContext ctx, User user) {
        String ans = ctx.io().readLine("Are you sure? type YES to confirm: ");
        if (!"YES".equalsIgnoreCase(ans.trim())) {
            System.out.println("Cancelled.");
            return;
        }
        ctx.dataService().deleteAllData();
        System.out.println("All data deleted.");
    }
}
