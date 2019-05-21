
import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var bookName: UITextField!
    override func viewDidLoad() {
        super.viewDidLoad()
         self.view.backgroundColor = UIColor(red: 90/255, green: 200/255, blue: 250/255, alpha: 1)
        // Do any additional setup after loading the view, typically from a nib.
    }
    @IBOutlet weak var AuthorName: UITextField!

    @IBOutlet weak var desc: UITextView!
    func generateString() -> String {
        let str = bookName.text! + ", " + AuthorName.text! + ", " + desc.text!
        return str
    }
    
    func getDocDirectory() -> URL {
        let paths = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let documentsDir = paths[0]
        return documentsDir
    }
    @IBAction func file(_ sender: Any) {
        let documentDirURL = getDocDirectory() // create the destination url for the text file to be saved
        let fileDestUrl = documentDirURL.appendingPathComponent("Assigment_FileManager.txt")
        print(fileDestUrl.path) //print file path
        let text = generateString()
        
        do {
            // writing to disk
            try text.write(to: fileDestUrl, atomically: false, encoding: String.Encoding.utf8)
            
            // saving was successful. any code posterior code goes here
            
            // reading from disk
            do {
                let mytext = try String(contentsOf: fileDestUrl, encoding: String.Encoding.utf8)
                print(mytext)   // "some text\n"
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
        let fullPath = getDocDirectory().appendingPathComponent("Assigment_Archive.dat")
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
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

