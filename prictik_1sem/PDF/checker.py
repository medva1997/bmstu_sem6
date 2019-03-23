# -*- coding: utf-8 -*-
from pprint import pprint
import os
import difflib

def format_newline(arr):

    k = 0
    
    for i in range(len(arr)):
        arr[i] = arr[i].strip()

        if arr[i] == '':
            k += 1

    for i in range(k):
        arr.remove('')

    return arr

def analyze_equal(data):

    dif1 = 0
    dif2 = 0
    match = 0

    for x in data:
        if x[0] == '-':
            dif1 += 1
        elif x[0] == '+':
            dif2 += 1
        elif x[0] != '?':
            match += 1

    return dif1, dif2, match

def count_uniqueness(data, match):

    data = format_newline(data)
    n = len(data)
    unique = match / n * 100
    return unique

def compare_files(file1, file2, percent=0):

    if open(file1).readlines() == []:
        return -1

    if open(file2).readlines() == []:
        return -1

    

    differ = difflib.Differ()

    data = list(differ.compare(open(file1).readlines(),open(file2).readlines()))
    data = format_newline(data)

    dif1, dif2, match = analyze_equal(data)

    unique = count_uniqueness(open(file1).readlines(), match)

    if unique>=percent :
        print('\nФайлы: ' + file1 + ' и ' + file2 + '\n')
        print('\n'.join(data))
        print('\n Оценка совпадения файлов = ' + str(int(unique)) + '%')
        print('+----------------------------------------------------------------------------------+')

    return unique , data


def menu():

    print('Введите вариант работы программы: ')
    print('1) Сравнение двух файлов')
    print('2) Сравнение двух директорий')
    print('3) Проверка всех файлов в папке')

    res = input('----> ')

    try:
        res = int(res)
    except e:
        print('Ошибка. Такого пунта меню не существует')
        menu()

    if (res < 1 or res > 3):
        print('Ошибка. Такого пунта меню не существует')
        menu()

    return res

def main():

    choice = menu()

    if (choice == 1):
        dir1 = input('Введите путь к первому файлу: ')
        dir2 = input('Введите путь ко второму файлу: ')

        dirs = os.listdir(".")
        if (dir1 in dirs and dir2 in dirs):
            unique, data =compare_files(file1, file2, 0)

        else:
            print('Ошибка при работе с файлами')

        return
    elif(choice == 2):

        dir1 = input('Введите путь к первой лабораторной: ')
        dir2 = input('Введите путь к первой лабораторной: ')

        dirs = os.listdir(".")

        if dir1 in dirs and dir2 in dirs:
           

            uniqueness = 0
            count = 0

            for file in files:
                dirs1 = os.listdir(dir1)
                dirs2 = os.listdir(dir2)

                if (file in dirs1 and file in dirs2):
                    res = compare_files(dir1 + '/' + file, dir2 + '/' + file, 0)
                    if res != -1:
                        uniqueness += res
                        count += 1


            if res > 0:
                res = uniqueness / count
                print('\n Оценка совпадения всей лабораторной работы = ' + str(int(res)) + '%')

        else:
            print("Ошибка при указании пути к лабораторным")
            return

        dir1 = 'lab1'
        dir2 = 'lab2'
    elif(choice == 3):

        dir0 = input('Введите путь к проверяемой директории: ')
        if os.path.isdir(dir0)==False:
             print('Директория не существует')
             return
        try:
            percent = int(input('Введите процент минимального совпадения: '))
            percent = int(percent)
        except:
            print('Ошибка ввода')
            return

        files = []
        find_files( files, dirs=[dir0], extensions=['.py',''] )
        #print(files)

        for i in range(0,len(files)):
            for j in range(i+1,len(files)):
                    unique=compare_files(files[i], files[j], percent)



    else:
         print ("Ошибка.")




def find_files( files, dirs=[], extensions=[]):
    new_dirs = []
    for d in dirs:
        try:
            new_dirs += [ os.path.join(d, f) for f in os.listdir(d) ]
        except OSError:
            if extensions == ['*']:
                files.append(d)
            else:
                if os.path.splitext(d)[1] in extensions:
                    files.append(d)

    if new_dirs:
        find_files(files, new_dirs, extensions )
    else:
        return



main()
#files = []
#find_files( files, dirs=['bmstu_test'], extensions=['.py',''] )
#print(files)