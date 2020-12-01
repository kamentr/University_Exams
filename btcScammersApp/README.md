##########################################################################################################################################################################

    This is an application helping people investigate whether a BTC Wallet might be of possession to a person with
    malicious intent. By providing the suspected wallet's address you can easily get a "Score" of how likely it is
    this wallet to be used by a scammer.

    An advanced algorithm goes through the past behavior of the suspect's wallet and looks for suspicious patterns like:
        - opposite transactions of the same value, meaning the account receives X amount of BTC and then immediately transfers it to another account
        - has identical or close to identical total-received and total-sent amounts, meaning his balance is close to 0 despite high traffic
        - and more
        Each of these is assigned a score and if the suspect falls into any of these categories his "malicious" score rises.

        The higher the "malicious" score is the riskier it is to send money to this account.

        BE AWARE! This app is just representing data in user friendly manner.
        DO NOT take any information from this app as an advice or as a fact.

##########################################################################################################################################################################

    To run locally you need Lombok install on your IDE

        Go to File > Settings > Plugins
        Click on Browse repositories...
        Search for Lombok Plugin
        Click on Install plugin
        Restart Android Studio

##########################################################################################################################################################################