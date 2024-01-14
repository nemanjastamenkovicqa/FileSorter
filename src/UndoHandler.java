import java.io.File;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class UndoHandler {

    private List<SortingAction> sortingHistory;

    public UndoHandler() {
        this.sortingHistory = new ArrayList<>();
    }

    public void recordSortingAction(File source, File destination) {
        SortingAction sortingAction = new SortingAction(source, destination);
        sortingHistory.add(sortingAction);
    }

    public void undoLastAction() {
        if (!sortingHistory.isEmpty()) {
            SortingAction lastAction = sortingHistory.remove(sortingHistory.size() - 1);
            // Undo the sorting action (e.g., move the file back to its original location)
            File source = lastAction.getDestination();
            File destination = lastAction.getSource();

            if (source.exists() && destination.exists()) {
                try {
                    java.nio.file.Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    private static class SortingAction {
        private final File source;
        private final File destination;

        public SortingAction(File source, File destination) {
            this.source = source;
            this.destination = destination;
        }

        public File getSource() {
            return source;
        }

        public File getDestination() {
            return destination;
        }
    }
}