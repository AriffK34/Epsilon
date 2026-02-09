from mcrcon import MCRcon
import sys

HOST = "localhost"
PORT = 25575
PASSWORD = "1234"

def reload_server():
    try:
        with MCRcon(HOST, PASSWORD, port=PORT) as mcr:
            response = mcr.command("bukkit:reload confirm")
            print("Server reload command sent successfully.")
            print("Response:", response)
    except Exception as e:
        print("An error occurred while trying to reload the server:", e)

if __name__ == "__main__":
    reload_server()