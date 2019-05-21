
import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBOutlet weak var bookName: UITextField!

    @IBOutlet weak var authorName: UITextField!

    @IBOutlet weak var desc: UITextView!
    
    @IBAction func file(_ sender: Any) {
        let documentDirURL = getDocDirectory()
        let fileDestUrl = documentDirURL.appendingPathComponent("FileStorageAndArchive.txt")
        print(fileDestUrl.path)
        let text = generateString()
        
        do {
            try text.write(to: fileDestUrl, atomically: false, encoding: String.Encoding.utf8)
            
            do {
                let mytext = try String(contentsOf: fileDestUrl, encoding: String.Encoding.utf8)
                print(mytext)
            } catch let error as NSError {
                print("error loading from url \(String(describing: fileDestUrl))")
                print(error.localizedDescription)
            }
        } catch let error as NSError {
            print("error on writing to the url \(String(describing: fileDestUrl))")
            print(error.localizedDescription)
        }
        
    }
    @IBAction func storage(_ sender: Any) {
        let text = generateString()
        let data = NSKeyedArchiver.archivedData(withRootObject: text)
        let fullPath = getDocDirectory().appendingPathComponent("FileStorageAndArchive.ar")
        print(fullPath.path)
        do {
            try data.write(to: fullPath)
        } catch {
            print("Could not write the file")
        }
        
        if let loadedStrings = NSKeyedUnarchiver.unarchiveObject(withFile: fullPath.path) {
            print("read back from the archives: \(loadedStrings)")
        } else {
            print("read back failed");
        }
    }
    
    
    func generateString() -> String {
        let str = bookName.text! + ", " + authorName.text! + ", " + desc.text!
        return str
    }
    
    func getDocDirectory() -> URL {
        let paths = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let documentsDir = paths[0]
        return documentsDir
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }


}

