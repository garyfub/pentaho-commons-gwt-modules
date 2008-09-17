package org.pentaho.gwt.widgets.client.filechooser;

import org.pentaho.gwt.widgets.client.dialogs.IDialogCallback;
import org.pentaho.gwt.widgets.client.dialogs.IDialogValidatorCallback;
import org.pentaho.gwt.widgets.client.dialogs.MessageDialogBox;
import org.pentaho.gwt.widgets.client.dialogs.PromptDialogBox;
import org.pentaho.gwt.widgets.client.filechooser.FileChooser.FileChooserMode;

import com.google.gwt.xml.client.Document;

public class FileChooserDialog extends PromptDialogBox {

  FileChooser fileChooser;

  public FileChooserDialog(FileChooserMode mode, String selectedPath, boolean autoHide, boolean modal) {
    super(mode == FileChooserMode.OPEN ? "Open" : "Save", mode == FileChooserMode.OPEN ? "Open" : "Save", "Cancel", autoHide, modal, new FileChooser(mode,
        selectedPath));
    fileChooser = (FileChooser) getContent();
    setValidatorCallback(new IDialogValidatorCallback() {
      public boolean validate() {
        boolean isValid = fileChooser.getActualFileName() != null && !"".equals(fileChooser.getActualFileName());
        if (!isValid) {
          MessageDialogBox dialogBox = new MessageDialogBox("Error", "No filename has been entered.", false, false, true);
          dialogBox.center();
        }
        return isValid;
      }
    });
    IDialogCallback callback = new IDialogCallback() {

      public void cancelPressed() {
      }

      public void okPressed() {
        fileChooser.fireFileSelected();
      }

    };
    setCallback(callback);
  }

  public FileChooserDialog(FileChooserMode mode, String selectedPath, Document repositoryDocument, boolean autoHide, boolean modal) {
    super(mode == FileChooserMode.OPEN ? "Open" : "Save", mode == FileChooserMode.OPEN ? "Open" : "Save", "Cancel", autoHide, modal, new FileChooser());
    fileChooser = (FileChooser) getContent();
    fileChooser.setMode(mode);
    fileChooser.setSelectedPath(selectedPath);
    fileChooser.solutionRepositoryDocument = repositoryDocument;
    fileChooser.repositoryTree = TreeBuilder.buildSolutionTree(repositoryDocument, fileChooser.showHiddenFiles, fileChooser.showLocalizedFileNames);
    setValidatorCallback(new IDialogValidatorCallback() {
      public boolean validate() {
        boolean isValid = fileChooser.getActualFileName() != null && !"".equals(fileChooser.getActualFileName());
        if (!isValid) {
          MessageDialogBox dialogBox = new MessageDialogBox("Error", "No filename has been entered.", false, false, true);
          dialogBox.center();
        }
        return isValid;
      }
    });
    IDialogCallback callback = new IDialogCallback() {

      public void cancelPressed() {
      }

      public void okPressed() {
        fileChooser.fireFileSelected();
      }

    };
    setCallback(callback);
    fileChooser.initUI(false);
  }

  public void addFileChooserListener(FileChooserListener listener) {
    fileChooser.addFileChooserListener(listener);
  }

  public void removeFileChooserListener(FileChooserListener listener) {
    fileChooser.removeFileChooserListener(listener);
  }

  public void setShowSearch(boolean showSearch) {
    fileChooser.setShowSearch(showSearch);
  }

  public boolean isShowSearch() {
    return fileChooser.isShowSearch();
  }

  public boolean doesSelectedFileExist() {
    return fileChooser.doesSelectedFileExist();
  }

}
