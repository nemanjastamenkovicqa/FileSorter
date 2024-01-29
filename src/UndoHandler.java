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
        // Record the original file paths before sorting
        File originalSource = new File(destination.getAbsolutePath());
        File originalDestination = new File(source.getAbsolutePath());
        SortingAction sortingAction = new SortingAction(originalSource, originalDestination);
        sortingHistory.add(sortingAction);
    }

    public void undoLastAction() {
        if (!sortingHistory.isEmpty()) {
            SortingAction lastAction = sortingHistory.remove(sortingHistory.size() - 1);
            // Undo the sorting action (e.g., move the file back to its original location)
            File originalSource = lastAction.getSource();
            File originalDestination = lastAction.getDestination();

            if (originalSource.exists() && originalDestination.exists()) {
                try {
                    java.nio.file.Files.move(originalSource.toPath(), originalDestination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Undo: Moved " + originalSource.getName() + " back to " + originalDestination.getParent());
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public void displaySortingHistory() {
        System.out.println("Sorting History:");
        for (SortingAction action : sortingHistory) {
            System.out.println("Original Source: " + action.getSource().getPath());
            System.out.println("Original Destination: " + action.getDestination().getPath());
            System.out.println("-----");
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
