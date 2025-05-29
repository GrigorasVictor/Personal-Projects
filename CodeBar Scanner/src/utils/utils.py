import cv2
from cv2 import Mat
import torch

from tkinter import Tk
from tkinter.filedialog import askopenfilename
from ultralytics import YOLO
from pyzbar.pyzbar import decode


class Utils:
    def __init__(self, model = "barcode-scanner.pt"):
        self.model = YOLO(model)
        self.device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
        print(self.device)

    def select_and_load_image(self):
        Tk().withdraw()
        image_path = askopenfilename(filetypes=[("Image files", "*.jpg;*.jpeg;*.png;*.bmp;*.tiff")])

        if not image_path:
            print("No file selected.")
            return None

        image = cv2.imread(image_path, cv2.IMREAD_COLOR)
        if image is None:
            raise FileNotFoundError(f"Failed to load image from path: {image_path}")
        return image, cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    def display_image_cv(self, image, title="Selected Image"):
        image = cv2.resize(image, (800, 600))
        cv2.imshow(title, image)
        cv2.waitKey(0)
        cv2.destroyAllWindows()

    def get_barcode(self, image: Mat) -> dict:
        classes = ["EAN13","PDF417","DataMatrix","QRCode","RoyalMailCode","Kix","Code128",
                   "UPCA","Aztec","Interleaved25","JapanPost","Code39","Postnet","UCC128",
                   "IntelligentMail","2-Digit","EAN8","IATA25"]

        predictions = self.model.predict(image, conf=0.6, iou=0.5, device=self.device)[0]
        result = {}
        for box in predictions.boxes:
            bbox = box.xyxy.tolist()[0]
            cls = classes[int(box.cls.tolist()[0])]  # Convert class to string
            points = [[bbox[0], bbox[1]], [bbox[2], bbox[1]], [bbox[2], bbox[3]], [bbox[0], bbox[3]]]  # Points
            if cls not in result:
                result[cls] = []
            result[cls].append(points)
        return result

    def process_image_barcode(self, gray_image: Mat) -> (Mat, list):
        _, processed = cv2.threshold(gray_image, 200, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

        #check if the image is vertically
        objects = None
        if processed.shape[0] > processed.shape[1]:
            objects = decode(cv2.rotate(processed, cv2.ROTATE_90_CLOCKWISE))
            if not objects:
                objects = decode(cv2.rotate(processed, cv2.ROTATE_90_COUNTERCLOCKWISE))

        return processed, objects
