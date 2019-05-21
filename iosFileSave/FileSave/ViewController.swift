
import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBOutlet weak var customerName: UITextField!

    @IBOutlet weak var phoneNumber: UITextField!

    @IBOutlet weak var comments: UITextView!
    
    
    @IBAction func file(_ sender: Any) {
        let documentDirURL = getDocDirectory()
        let fileDestUrl = documentDirURL.appendingPathComponent("FileSave.txt")
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
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    func generateString() -> String {
        let str = customerName.text! + ", " + phoneNumber.text! + ", " + comments.text!
        return str
    }
    
    func getDocDirectory() -> URL {
        let paths = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        let documentsDir = paths[0]
        return documentsDir
    }
    
    
}

