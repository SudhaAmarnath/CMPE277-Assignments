//
//  ViewController.swift
//  temperatureConverterSegue
//
//  Created by admin on 5/11/19.
//  Copyright © 2019 admin. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var textField: UITextField!
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        let recieverVC = segue.destination as! ReceiverVC
        
        if let text = textField.text {
            
            let farhenheitValue:Float = Float(text)!
            recieverVC.text = String((farhenheitValue - 32)*0.556)
    
        }
        
        
        
    }
}

