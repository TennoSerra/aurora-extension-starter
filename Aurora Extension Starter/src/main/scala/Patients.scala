import typings.vscode.mod.{TreeDataProvider, TreeItem, TreeItemCollapsibleState, EventEmitter, Event}
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportTopLevel, JSGlobal}
import scala.scalajs.js.{Promise, UndefOr}

@JSExportTopLevel("Patient")
class Patient(val name: String, val age: Int, val collapsibleState: TreeItemCollapsibleState) extends js.Object
@js.native
@JSGlobal("vscode.TreeDataProvider")
trait NativeTreeDataProvider[T] extends js.Object {
  def getTreeItem(element: T): TreeItem
  def getChildren(element: UndefOr[T]): Promise[js.Array[T]]
  def onDidChangeTreeData: Event[UndefOr[T]]
}

@JSExportTopLevel("PatientTreeProvider")
class PatientTreeProvider extends NativeTreeDataProvider[Patient] {
  private val _onDidChangeTreeData: EventEmitter[js.UndefOr[Patient]] = new EventEmitter[js.UndefOr[Patient]]()

  // Event fired when the tree data changes
  def onDidChangeTreeData: Event[js.UndefOr[Patient]] = onDidChangeTreeData

  // Example patients
  private var patients: js.Array[Patient] = js.Array(
    new Patient("John Doe", 29, TreeItemCollapsibleState.None),
    new Patient("Jane Smith", 34, TreeItemCollapsibleState.None)
  )

  def refresh(): js.Function1[Any,Any] = {
    (arg) =>  _onDidChangeTreeData.fire(js.undefined)
  }

  // Get a tree item for the view
  def getTreeItem(element: Patient): TreeItem = {
    new TreeItem(s"${element.name} (Age: ${element.age})", element.collapsibleState)
  }

  // Get the children (the list of patients)
  def getChildren(element: js.UndefOr[Patient]): Promise[js.Array[Patient]] = {
    Promise.resolve(patients)
  }
}

