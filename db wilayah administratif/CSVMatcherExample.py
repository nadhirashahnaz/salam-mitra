import csv

def read_csv_file(filename):
    data = []
    with open(filename, 'r', encoding='utf-8') as file:
        csv_reader = csv.reader(file)
        for row in csv_reader:
            data.append(row)
    return data

def generate_sql_insert(provinces, regencies, districts):
    province_dict = {province[0]: province[1] for province in provinces}
    regency_dict = {regency[0]: (regency[1], regency[2]) for regency in regencies}
    district_dict = {district[0]: district[2] for district in districts}

    sql_commands = []
    counter = 1
    for district in districts:
        district_id = district[0]
        regency_id = district_id[0:4]
        province_id = regency_id[0:2]

        province_name = province_dict.get(province_id, "Unknown Province")
        regency_name, regency_full_name = regency_dict.get(regency_id, ("Unknown Regency", "Unknown Regency"))
        district_name = district_dict.get(district_id, "Unknown District")

        sql_command = f"INSERT INTO LOKASI VALUES ({counter}, '{regency_full_name}', '{district_name}', '{province_name}');"
        sql_commands.append(sql_command)
        counter += 1

    return sql_commands

if __name__ == "__main__":
    provinces = read_csv_file('provinces.csv')
    regencies = read_csv_file('regencies.csv')
    districts = read_csv_file('districts.csv')

    sql_commands = generate_sql_insert(provinces, regencies, districts)
    with open('output.sql', 'w') as file:
        for sql_command in sql_commands:
            file.write(sql_command + '\n')
