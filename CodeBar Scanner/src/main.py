import warnings
from utils.utils import Utils
import cv2
from pyzbar.pyzbar import decode

warnings.filterwarnings("ignore", message="The NumPy module was reloaded.*")
utils = Utils("barcode-scanner.pt")

while True:
    image, gray_image = utils.select_and_load_image()
    utils.display_image_cv(image, "Original Image")
    barcode_xyxy = utils.get_barcode(image)
    print(barcode_xyxy)

    # Crop the image based on the detected barcodes
    for type, points in barcode_xyxy.items():
        for point in points:
            int_points = [list(map(int, p)) for p in point]
            x_min = min(p[0] for p in int_points)
            y_min = min(p[1] for p in int_points)
            x_max = max(p[0] for p in int_points)
            y_max = max(p[1] for p in int_points)

            cv2.rectangle(image, (x_min, y_min), (x_max, y_max), (0, 255, 0), 2)
            cv2.putText(image, type, (x_min, y_min - 10), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)

            _, decoded_objects = utils.process_image_barcode(gray_image[y_min:y_max, x_min:x_max])

            if decoded_objects:
                for obj in decoded_objects:
                    print("Decoded Information:", obj.data.decode("utf-8"))

                    if obj.type == "QRCODE":
                        cv2.rectangle(image, (x_min, y_max + 10), (x_min + 1000, y_max + 50), (0, 0, 0), -1)
                    else:
                        cv2.rectangle(image, (x_min, y_max + 10), (x_min + 300, y_max + 50), (0, 0, 0), -1)
                    cv2.putText(image, obj.data.decode("utf-8"), (x_min, y_max + 40), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)
        else:
                print("No barcode detected.")

    utils.display_image_cv(image, "Final result")
    cv2.waitKey(0)
