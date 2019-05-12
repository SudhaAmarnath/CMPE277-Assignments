//
//  ReceiverVC.swift
//  temperatureConverterSegue
//
//  Created by admin on 5/11/19.
//  Copyright © 2019 admin. All rights reserved.
//

import UIKit

class ReceiverVC: UIViewController {

    
    var text:String?
    
    @IBOutlet weak var label: UILabel!
    override func viewDidLoad() {

        if let receivedText = text {
            label.text = receivedText
        }
    }
    

}
