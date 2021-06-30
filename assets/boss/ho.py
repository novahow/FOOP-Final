#!/usr/bin/env python3
# -*- coding: utf-8 -*-
from PIL import Image

for i in range(130):
    img = Image.open("attack/" + str(i) +".png")
# img.show()

    new_img = img.transpose(Image.FLIP_LEFT_RIGHT)
    new_img.save("attack/" + str(i) +".png")
# new_img.show()