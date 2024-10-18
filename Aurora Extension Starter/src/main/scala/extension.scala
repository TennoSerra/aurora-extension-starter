import typings.vscode.mod as vscode
import typings.vscode.anon.Dispose
import typings.vscode.mod.{TreeDataProvider, Disposable, commands, window, ExtensionContext}
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel



@JSExportTopLevel("activate")
def activate(context: ExtensionContext): Unit = {
  // Create an instance of PatientTreeProvider
  val patientTreeProvider = new PatientTreeProvider()

  // Register the Tree View for patients
  window.registerTreeDataProvider("auroraExtension.patientTree", patientTreeProvider.asInstanceOf[TreeDataProvider[?]])
   

  // Register the command to refresh patients
  context.subscriptions.push(
    commands.registerCommand("auroraExtension.refreshPatients", patientTreeProvider.refresh()).asInstanceOf[Dispose]
  )
}

@JSExportTopLevel("deactivate")
def deactivate(): Unit = {
  // Clean up resources or perform any necessary cleanup actions here
}
